package org.polystat.py2eo

import Expression._

object PrintPython {

  private def printComprehension(e : Comprehension) : String =
    e match {
      case f : ForComprehension =>
        "%sfor %s in %s".format(if (f.isAsync) "async " else "", printExpr(f.what), printExpr(f.in))
      case x : IfComprehension => "if %s".format(printExpr(x.cond))
    }

  private def printDictElt(x : DictEltDoubleStar) = x match {
    case Left(value) =>  "%s : %s".format(printExpr(value._1), printExpr(value._2))
    case Right(value) =>  "**%s".format(printExpr(value))
  }

  def printExpr(e : T) : String = {
    def brak(s : String, open : String = "(", close : String = ")") = s"$open$s$close"
    def rnd(s : String) = brak(s, "(", ")")
    def sqr(s : String) : String = brak(s, "[", "]")
    e match {
      case Await(what, _) => brak("await %s".format(printExpr(what)))
      case NoneLiteral(_) => "None"
      case EllipsisLiteral(_) => "..."
      case UnsupportedExpr(_, _) => "None"
      case IntLiteral(value, _) => s"$value "
      case FloatLiteral(value, _) => value
      case ImagLiteral(value, _) => s"${value}j"
      case StringLiteral(values, _) => values.mkString("")
      case BoolLiteral(b, _) => if (b) "True" else "False"
      case Binop(op, l, r, _) => brak("%s %s %s".format(printExpr(l), Binops.toString(op), printExpr(r)))
      case LazyLOr(l, r, _) => rnd("%s or %s".format(printExpr(l), printExpr(r)))
      case LazyLAnd(l, r, _) => rnd("%s and %s".format(printExpr(l), printExpr(r)))
      case SimpleComparison(op, l, r, _) => brak("%s %s %s".format(printExpr(l), Compops.toString(op), printExpr(r)))
      case FreakingComparison(ops, l, _) =>
        val sops = ops.map(Compops.toString) :+ ""
        val sopnds = l.map(printExpr)
        brak(sopnds.zip(sops).flatMap(x => List(x._1, x._2)).mkString(" "))
      case Unop(op, x, _) => brak(Unops.toString(op) + printExpr(x))
      case Ident(name, _) => name
      case Star(e, _) => "*%s".format(printExpr(e))
      case DoubleStar(e, _) => "**%s".format(printExpr(e))
      case Slice(from, to, by, _) =>
        def procBound(b : Option[T]) = b match {
          case None => ""
          case Some(e) => printExpr(e)
        }
        "%s:%s:%s".format(procBound(from), procBound(to), procBound(by))
      case CallIndex(false, whom, List((_, CollectionCons(CollectionKind.Tuple, l, _))), _) if l.nonEmpty =>
        "%s[%s%s]".format(printExpr(whom), l.map(printExpr).mkString(", "), if (l.size == 1) "," else "")
      case CallIndex(isCall, whom, args, _) => printExpr(whom) + (if (isCall) rnd _ else sqr _)(
        args.map{case (None, e) => printExpr(e)  case (Some(keyword), e) => s"$keyword=${printExpr(e)}"}.mkString(", ")
      )
      case Field(whose, name, _) => "%s.%s".format(printExpr(whose), name)
      case Cond(cond, yes, no, _) => "%s if %s else %s".format(printExpr(yes), printExpr(cond), printExpr(no))
      case AnonFun(args, otherPositional, otherKeyword, body, _) =>
         "(lambda %s : %s)".format(
           printArgs(args, otherPositional.map(x => (x, None)), otherKeyword.map(x => (x, None))),
           printExpr(body)
         )
      case CollectionCons(kind, l, _) =>
        val braks = CollectionKind.toBraks(kind)
        brak("%s%s".format(l.map(printExpr).mkString(", "), if (l.size == 1) "," else ""), braks._1, braks._2)
      case CollectionComprehension(kind, base, l, _) =>
        val braks = CollectionKind.toBraks(kind)
        brak("%s %s".format(printExpr(base), l.map(printComprehension).mkString(" ")), braks._1, braks._2)
      case GeneratorComprehension(base, l, _) =>
        "(%s %s)".format(printExpr(base), l.map(printComprehension).mkString(" "))
      case DictCons(l, _) => brak(l.map(printDictElt).mkString(", "), "{", "}")
      case DictComprehension(base, l, _) =>
        "{%s %s}".format(printDictElt(base), l.map(printComprehension).mkString(" "))
      case Yield(Some(e), _) => brak("yield %s".format(printExpr(e)))
      case Yield(None, _) =>  "(yield)"
      case YieldFrom(e, _) => "(yield from %s)".format(printExpr(e))
    }
  }

  def option2string[T](x : Option[T]): String = x match {
    case Some(value) => value.toString
    case None => ""
  }

  def printSt(s : Statement, indentAmount : String) : String = {
    def async(isAsync : Boolean) = if (isAsync) "async " else ""
    val indentIncrAmount = indentAmount + "    "
    def indentPos(str : String) : String = "%s%s # %s".format(indentAmount, str, s.ann)
    def printDecorators(decorators: Decorators) =
      decorators.l.map(z => "%s@%s\n".format(indentAmount, printExpr(z))).mkString("")
    s match {
      case _: Unsupported => indentPos("assert(false)")
      case Del(e, _) => indentPos("del %s".format(printExpr(e)))
      case With(cm, target, body, isAsync, _) =>
        val targetString = target match {
          case Some(value) => " as " + printExpr(value)
          case None => ""
        }
        "%s%swith %s%s: #%s\n%s".format(
          indentAmount, async(isAsync), printExpr(cm), targetString, s.ann, printSt(body, indentIncrAmount)
        )
      case If(conditioned, eelse, _) =>
        def oneCase(keyword : String, p : (T, Statement)) = {
          "%s%s (%s): # %s \n%s".format(
            indentAmount, keyword, printExpr(p._1), p._2.ann.toString,
            printSt(p._2, indentIncrAmount)
          )
        }
        val iif :: elifs = conditioned
        val elseString = "%selse: # %s\n%s".format(indentAmount, eelse.ann.toString, printSt(eelse, indentIncrAmount))
        (oneCase("if", iif) :: elifs.map(oneCase("elif", _))).mkString("\n") + "\n" + elseString
      case IfSimple(cond, yes, no, ann) => printSt(If(List((cond, yes)), no, ann.pos), indentAmount)
      case While(cond, body, eelse, _) =>
        "%swhile (%s): # %s\n%s\n%selse:\n%s".format(
          indentAmount, printExpr(cond), s.ann, printSt(body, indentIncrAmount),
          indentAmount, printSt(eelse, indentIncrAmount)
        )
      case For(what, in, body, eelse, isAsync, _) =>
        "%s%sfor %s in %s: # %s\n%s\n%selse:\n%s".format(
          indentAmount, async(isAsync), printExpr(what), printExpr(in), s.ann,
          printSt(body, indentIncrAmount),
          indentAmount,
          printSt(eelse, indentIncrAmount)
        )
      case Try(ttry, excepts, eelse, ffinally, _) =>
        val elseString = if (excepts.isEmpty) "" else {
          "%selse:\n%s\n".format(
            indentAmount,
            printSt(eelse, indentIncrAmount)
          )
        }
        "%stry: # %s\n%s\n%s\n%s%sfinally:\n%s".format(
          indentAmount, s.ann,
          printSt(ttry, indentIncrAmount),
          excepts.map(x =>
            "%sexcept %s:\n%s".format(
              indentAmount,
              option2string(x._1.map(y => printExpr(y._1) + option2string(y._2.map(z => " as " + z)))),
              printSt(x._2, indentIncrAmount)
            )
          ).mkString("\n"),
          elseString,
          indentAmount,
          printSt(ffinally, indentIncrAmount)
        )
      case Suite(l, _) => l.map(printSt(_, indentAmount)).mkString("\n")
      case AugAssign(op, lhs, rhs, _) => indentPos("%s%s%s".format(printExpr(lhs), AugOps.toString(op), printExpr(rhs)))
      case Assign(l, _) => indentPos(l.map(printExpr).mkString(" = "))
      case AnnAssign(lhs, rhsAnn, rhs, _) =>
        "%s%s : %s%s".format(
          indentAmount, printExpr(lhs), printExpr(rhsAnn),
          rhs match { case None => "" case Some(e) => " = %s".format(printExpr(e))}
        )
      case CreateConst(name, value, ann) =>
        printSt(Assign(List(Ident(name, value.ann.pos), value), ann.pos.pos), indentAmount)
      case Break(_) => indentPos("break")
      case Continue(_) => indentPos("continue")
      case Pass(_) => indentPos("pass")
      case Return(Some(x), _) => indentPos("return %s".format(printExpr(x)))
      case Return(None, _) => indentPos("return ")
      case Assert(l, _) => indentPos("assert %s".format(l.map(printExpr).mkString(", ")))
      case Raise(Some(x), Some(from), _) => indentPos("raise %s from %s".format(printExpr(x), printExpr(from)))
      case Raise(Some(x), None, _) => indentPos("raise %s".format(printExpr(x)))
      case Raise(None, None, _) => indentPos("raise")
      case NonLocal(l, _) => indentPos("nonlocal %s".format(l.mkString(", ")))
      case Global(l, _) => indentPos("global %s".format(l.mkString(", ")))
      case SimpleObject(name, fields, ann) =>
        "%sclass %s: # %s\n%s".format(
          indentAmount, name, s.ann,
            printSt(
              Suite(fields.map(z => Assign(List(Ident(z._1, z._2.ann.pos), z._2), z._2.ann.pos)), ann.pos),
              indentIncrAmount
            )
        )
      case ClassDef(name, bases, body, decorators, _) =>
        "%s%sclass %s(%s): # %s\n%s".format(
          printDecorators(decorators),
          indentAmount, name,
          bases.map(
            x => {
              val r = printExpr(x._2)
              x._1 match {
                case None => r
                case Some(name) => s"$name=$r"
              }
            }
          ).mkString(", "), s.ann,
          printSt(body, indentIncrAmount)
        )
      case FuncDef(name, args, otherPositional, otherKeyword, returnAnnotation, body, decorators, _, isAsync, _) =>
        val retAnn = returnAnnotation match { case None => "" case Some(e) => " -> " + printExpr(e) }
        "%s%s%sdef %s(%s)%s: # %s\n%s".format(
          printDecorators(decorators),
          indentAmount, async(isAsync), name,
          printArgs(args, otherPositional, otherKeyword), retAnn, s.ann,
          printSt(body, indentIncrAmount)
        )
      case ImportModule(what, as, _) =>
        indentPos("import %s%s".format(what.mkString("."), as match { case None => "" case Some(x) => s" as $x"}))
      case ImportSymbol(from, what, as, _) => indentPos(s"from ${from.mkString(".")} import $what as $as")
      case ImportAllSymbols(from, _) => indentPos(s"from ${from.mkString(".")} import *")
    }
  }

  def printArgs(args : List[Parameter], otherPositional : Option[(String, Option[T])],
                otherKeyword : Option[(String, Option[T])]) : String = {
    val positionalOnly = args.filter(_.kind == ArgKind.Positional)
    val posOrKeyword = args.filter(_.kind == ArgKind.PosOrKeyword)
    val keywordOnly = args.filter(_.kind == ArgKind.Keyword)
    assert(positionalOnly ++ posOrKeyword ++ keywordOnly == args)
    def f(pref : String, z : Option[(String, Option[T])]) : List[String] =
      z match {
        case Some((name, None)) => List(pref + name)
        case Some((name, Some(typAnn))) => List(pref + name + " : " + printExpr(typAnn))
        case None => if (pref != "*" || keywordOnly.isEmpty) List() else List(pref)
      }
    def printArg(x : Parameter) =
      x.name +
        (x.paramAnn match { case None => "" case Some(value) => " : " + printExpr(value)}) +
        (x.default match { case None => "" case Some(default) => " = " + printExpr(default)})
    val argstring = positionalOnly.map(printArg) ++
      (if (positionalOnly.isEmpty) List() else List("/")) ++
      posOrKeyword.map(printArg) ++ f("*", otherPositional) ++
      keywordOnly.map(printArg) ++ f("**", otherKeyword)
    argstring.mkString(", ")
  }

}

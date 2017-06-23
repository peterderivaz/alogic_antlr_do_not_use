package alogic.ast

// Various representational case classes not descendant from ast.Node

// // Declaration used for top-level and function declarations
sealed trait Declaration
// TODO: Add separate Decl for Arrays, it just makes a lot of things simpler
case class VarDeclaration(decltype: Type, id: VarRef, init: Option[Expr]) extends Declaration
case class ParamDeclaration(decltype: Type, id: String, init: Expr) extends Declaration
case class ConstDeclaration(decltype: Type, id: String, init: Expr) extends Declaration
case class VerilogDeclaration(decltype: Type, id: VarRef) extends Declaration
case class OutDeclaration(synctype: SyncType, decltype: Type, name: String) extends Declaration
case class InDeclaration(synctype: SyncType, decltype: Type, name: String) extends Declaration

// AlogicType used to define the allowed types
sealed trait Type
case class IntType(signed: Boolean, size: Int) extends Type
case class IntVType(signed: Boolean, args: List[Expr]) extends Type // variable number of bits definition
case class Struct(fields: Map[String, Type]) extends Type
case object State extends Type // Type with enough bits to hold state variable

// SyncType for allowed port types
sealed trait SyncType
case object SyncReadyBubble extends SyncType
case object SyncReady extends SyncType
case object SyncAccept extends SyncType
case object Sync extends SyncType
case object WireSyncAccept extends SyncType
case object WireSync extends SyncType
case object Wire extends SyncType

// Signal channel which has a name, some payload and possibly some flow control signals
sealed trait Channel {
  val name: String
  val width: Expr
}
case class ChannelNone(name: String, width: Expr) extends Channel // no flow control
case class ChannelValid(name: String, width: Expr) extends Channel // valid only
case class ChannelReady(name: String, width: Expr) extends Channel // valid + ready
case class ChannelAccept(name: String, width: Expr) extends Channel // valid + accept

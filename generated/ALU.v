module ALU(
  input         clock,
  input         reset,
  input  [5:0]  io_sel,
  input  [31:0] io_oper1,
  input  [31:0] io_oper2,
  output        io_comparisonResult,
  output [31:0] io_result
);
  wire  _T = 6'h0 == io_sel; // @[Conditional.scala 37:30]
  wire [31:0] _T_2 = io_oper1 + io_oper2; // @[ALU.scala 20:29]
  wire  _T_3 = 6'h1 == io_sel; // @[Conditional.scala 37:30]
  wire [31:0] _T_5 = io_oper1 - io_oper2; // @[ALU.scala 24:29]
  wire  _T_6 = 6'h2 == io_sel; // @[Conditional.scala 37:30]
  wire [63:0] _T_7 = io_oper1 * io_oper2; // @[ALU.scala 28:29]
  wire  _T_8 = 6'h3 == io_sel; // @[Conditional.scala 37:30]
  wire  _T_11 = 6'h4 == io_sel; // @[Conditional.scala 37:30]
  wire  _T_14 = 6'h5 == io_sel; // @[Conditional.scala 37:30]
  wire  _T_16 = 6'h10 == io_sel; // @[Conditional.scala 37:30]
  wire  _T_17 = 6'h13 == io_sel; // @[Conditional.scala 37:30]
  wire  _T_18 = 6'h21 == io_sel; // @[Conditional.scala 37:30]
  wire  _T_19 = io_oper1 == io_oper2; // @[ALU.scala 52:20]
  wire  _T_20 = 6'h20 == io_sel; // @[Conditional.scala 37:30]
  wire  _GEN_2 = _T_18 ? _T_19 : _T_20; // @[Conditional.scala 39:67]
  wire [31:0] _GEN_3 = _T_17 ? io_oper1 : 32'h0; // @[Conditional.scala 39:67]
  wire  _GEN_4 = _T_17 ? 1'h0 : _GEN_2; // @[Conditional.scala 39:67]
  wire [31:0] _GEN_5 = _T_16 ? io_oper2 : _GEN_3; // @[Conditional.scala 39:67]
  wire  _GEN_6 = _T_16 ? 1'h0 : _GEN_4; // @[Conditional.scala 39:67]
  wire [63:0] _GEN_7 = _T_14 ? _T_7 : {{32'd0}, _GEN_5}; // @[Conditional.scala 39:67]
  wire  _GEN_8 = _T_14 ? 1'h0 : _GEN_6; // @[Conditional.scala 39:67]
  wire [63:0] _GEN_9 = _T_11 ? {{32'd0}, _T_5} : _GEN_7; // @[Conditional.scala 39:67]
  wire  _GEN_10 = _T_11 ? 1'h0 : _GEN_8; // @[Conditional.scala 39:67]
  wire [63:0] _GEN_11 = _T_8 ? {{32'd0}, _T_2} : _GEN_9; // @[Conditional.scala 39:67]
  wire  _GEN_12 = _T_8 ? 1'h0 : _GEN_10; // @[Conditional.scala 39:67]
  wire [63:0] _GEN_13 = _T_6 ? _T_7 : _GEN_11; // @[Conditional.scala 39:67]
  wire  _GEN_14 = _T_6 ? 1'h0 : _GEN_12; // @[Conditional.scala 39:67]
  wire [63:0] _GEN_15 = _T_3 ? {{32'd0}, _T_5} : _GEN_13; // @[Conditional.scala 39:67]
  wire  _GEN_16 = _T_3 ? 1'h0 : _GEN_14; // @[Conditional.scala 39:67]
  wire [63:0] _GEN_17 = _T ? {{32'd0}, _T_2} : _GEN_15; // @[Conditional.scala 40:58]
  assign io_comparisonResult = _T ? 1'h0 : _GEN_16; // @[ALU.scala 15:23 ALU.scala 54:29 ALU.scala 57:29 ALU.scala 62:29]
  assign io_result = _GEN_17[31:0]; // @[ALU.scala 14:13 ALU.scala 20:17 ALU.scala 24:17 ALU.scala 28:17 ALU.scala 32:17 ALU.scala 36:17 ALU.scala 40:17 ALU.scala 44:17 ALU.scala 48:17]
endmodule

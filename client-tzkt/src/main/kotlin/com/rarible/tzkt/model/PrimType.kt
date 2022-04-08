/**
 * TzKT API
 *
 * # Introduction  TzKT Explorer provides free REST API and WebSocket API for accessing detailed Tezos blockchain data and helps developers build more services and applications on top of Tezos. TzKT is an open-source project, so you can easily clone and build it and use it as a self-hosted service to avoid any risks of depending on third-party services.  TzKT API is available for the following Tezos networks with the following base URLs:  - Mainnet: `https://api.tzkt.io/` or `https://api.mainnet.tzkt.io/` ([view docs](https://api.tzkt.io))  - Granadanet: `https://api.granadanet.tzkt.io/` ([view docs](https://api.granadanet.tzkt.io))     - Hangzhou2net: `https://api.hangzhou2net.tzkt.io/` ([view docs](https://api.hangzhou2net.tzkt.io))  We also provide a staging environment for testing newest features and pre-updating client applications before deploying to production:  - Mainnet staging: `https://staging.api.tzkt.io/` or `https://staging.api.mainnet.tzkt.io/` ([view docs](https://staging.api.tzkt.io))  Feel free to contact us if you have any questions or feature requests. Your feedback really helps us make TzKT better!  - Discord: https://discord.gg/aG8XKuwsQd - Telegram: https://t.me/baking_bad_chat - Slack: https://tezos-dev.slack.com/archives/CV5NX7F2L - Twitter: https://twitter.com/TezosBakingBad - Email: hello@baking-bad.org  And don't forget to star TzKT project [on GitHub](https://github.com/baking-bad/tzkt) ;)  # Terms of Use  TzKT API is free for everyone and for both commercial and non-commercial usage.  If your application or service uses the TzKT API in any forms: directly on frontend or indirectly on backend, you should mention that fact on your website or application by placing the label **\"Powered by TzKT API\"** with a direct link to [tzkt.io](https://tzkt.io).   # Rate Limits  There will be no rate limits as long as our servers can handle the load without additional infrastructure costs. However, any apparent abuse will be prevented by setting targeted rate limits.  Check out [Tezos Explorer API Best Practices](https://baking-bad.org/blog/tag/TzKT/) and in particular [how to optimize requests count](https://baking-bad.org/blog/2020/07/29/tezos-explorer-api-tzkt-how-often-to-make-requests/).  --- 
 *
 * The version of the OpenAPI document: v1.7.0
 * Contact: hello@baking-bad.org
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.rarible.tzkt.models


import com.squareup.moshi.Json

/**
 * 
 *
 * Values: _0,_1,_2,_3,_4,_5,_6,_7,_8,_9,_10,_11,_12,_13,_14,_15,_16,_17,_18,_19,_20,_21,_22,_23,_24,_25,_26,_27,_28,_29,_30,_31,_32,_33,_34,_35,_36,_37,_38,_39,_40,_41,_42,_43,_44,_45,_46,_47,_48,_49,_50,_51,_52,_53,_54,_55,_56,_57,_58,_59,_60,_61,_62,_63,_64,_65,_66,_67,_68,_69,_70,_71,_72,_73,_74,_75,_76,_77,_78,_79,_80,_81,_82,_83,_84,_85,_86,_87,_88,_89,_90,_91,_92,_93,_94,_95,_96,_97,_98,_99,_100,_101,_102,_103,_104,_105,_106,_107,_108,_109,_110,_111,_112,_113,_114,_115,_116,_117,_118,_119,_120,_121,_122,_123,_124,_125,_126,_127,_128,_129,_130,_131,_132,_133,_134,_135,_136,_137,_138,_139,_140,_141,_142,_143,_144,_145,_146
 */

enum class PrimType(val value: kotlin.Int) {

    @Json(name = "0")
    _0(0),

    @Json(name = "1")
    _1(1),

    @Json(name = "2")
    _2(2),

    @Json(name = "3")
    _3(3),

    @Json(name = "4")
    _4(4),

    @Json(name = "5")
    _5(5),

    @Json(name = "6")
    _6(6),

    @Json(name = "7")
    _7(7),

    @Json(name = "8")
    _8(8),

    @Json(name = "9")
    _9(9),

    @Json(name = "10")
    _10(10),

    @Json(name = "11")
    _11(11),

    @Json(name = "12")
    _12(12),

    @Json(name = "13")
    _13(13),

    @Json(name = "14")
    _14(14),

    @Json(name = "15")
    _15(15),

    @Json(name = "16")
    _16(16),

    @Json(name = "17")
    _17(17),

    @Json(name = "18")
    _18(18),

    @Json(name = "19")
    _19(19),

    @Json(name = "20")
    _20(20),

    @Json(name = "21")
    _21(21),

    @Json(name = "22")
    _22(22),

    @Json(name = "23")
    _23(23),

    @Json(name = "24")
    _24(24),

    @Json(name = "25")
    _25(25),

    @Json(name = "26")
    _26(26),

    @Json(name = "27")
    _27(27),

    @Json(name = "28")
    _28(28),

    @Json(name = "29")
    _29(29),

    @Json(name = "30")
    _30(30),

    @Json(name = "31")
    _31(31),

    @Json(name = "32")
    _32(32),

    @Json(name = "33")
    _33(33),

    @Json(name = "34")
    _34(34),

    @Json(name = "35")
    _35(35),

    @Json(name = "36")
    _36(36),

    @Json(name = "37")
    _37(37),

    @Json(name = "38")
    _38(38),

    @Json(name = "39")
    _39(39),

    @Json(name = "40")
    _40(40),

    @Json(name = "41")
    _41(41),

    @Json(name = "42")
    _42(42),

    @Json(name = "43")
    _43(43),

    @Json(name = "44")
    _44(44),

    @Json(name = "45")
    _45(45),

    @Json(name = "46")
    _46(46),

    @Json(name = "47")
    _47(47),

    @Json(name = "48")
    _48(48),

    @Json(name = "49")
    _49(49),

    @Json(name = "50")
    _50(50),

    @Json(name = "51")
    _51(51),

    @Json(name = "52")
    _52(52),

    @Json(name = "53")
    _53(53),

    @Json(name = "54")
    _54(54),

    @Json(name = "55")
    _55(55),

    @Json(name = "56")
    _56(56),

    @Json(name = "57")
    _57(57),

    @Json(name = "58")
    _58(58),

    @Json(name = "59")
    _59(59),

    @Json(name = "60")
    _60(60),

    @Json(name = "61")
    _61(61),

    @Json(name = "62")
    _62(62),

    @Json(name = "63")
    _63(63),

    @Json(name = "64")
    _64(64),

    @Json(name = "65")
    _65(65),

    @Json(name = "66")
    _66(66),

    @Json(name = "67")
    _67(67),

    @Json(name = "68")
    _68(68),

    @Json(name = "69")
    _69(69),

    @Json(name = "70")
    _70(70),

    @Json(name = "71")
    _71(71),

    @Json(name = "72")
    _72(72),

    @Json(name = "73")
    _73(73),

    @Json(name = "74")
    _74(74),

    @Json(name = "75")
    _75(75),

    @Json(name = "76")
    _76(76),

    @Json(name = "77")
    _77(77),

    @Json(name = "78")
    _78(78),

    @Json(name = "79")
    _79(79),

    @Json(name = "80")
    _80(80),

    @Json(name = "81")
    _81(81),

    @Json(name = "82")
    _82(82),

    @Json(name = "83")
    _83(83),

    @Json(name = "84")
    _84(84),

    @Json(name = "85")
    _85(85),

    @Json(name = "86")
    _86(86),

    @Json(name = "87")
    _87(87),

    @Json(name = "88")
    _88(88),

    @Json(name = "89")
    _89(89),

    @Json(name = "90")
    _90(90),

    @Json(name = "91")
    _91(91),

    @Json(name = "92")
    _92(92),

    @Json(name = "93")
    _93(93),

    @Json(name = "94")
    _94(94),

    @Json(name = "95")
    _95(95),

    @Json(name = "96")
    _96(96),

    @Json(name = "97")
    _97(97),

    @Json(name = "98")
    _98(98),

    @Json(name = "99")
    _99(99),

    @Json(name = "100")
    _100(100),

    @Json(name = "101")
    _101(101),

    @Json(name = "102")
    _102(102),

    @Json(name = "103")
    _103(103),

    @Json(name = "104")
    _104(104),

    @Json(name = "105")
    _105(105),

    @Json(name = "106")
    _106(106),

    @Json(name = "107")
    _107(107),

    @Json(name = "108")
    _108(108),

    @Json(name = "109")
    _109(109),

    @Json(name = "110")
    _110(110),

    @Json(name = "111")
    _111(111),

    @Json(name = "112")
    _112(112),

    @Json(name = "113")
    _113(113),

    @Json(name = "114")
    _114(114),

    @Json(name = "115")
    _115(115),

    @Json(name = "116")
    _116(116),

    @Json(name = "117")
    _117(117),

    @Json(name = "118")
    _118(118),

    @Json(name = "119")
    _119(119),

    @Json(name = "120")
    _120(120),

    @Json(name = "121")
    _121(121),

    @Json(name = "122")
    _122(122),

    @Json(name = "123")
    _123(123),

    @Json(name = "124")
    _124(124),

    @Json(name = "125")
    _125(125),

    @Json(name = "126")
    _126(126),

    @Json(name = "127")
    _127(127),

    @Json(name = "128")
    _128(128),

    @Json(name = "129")
    _129(129),

    @Json(name = "130")
    _130(130),

    @Json(name = "131")
    _131(131),

    @Json(name = "132")
    _132(132),

    @Json(name = "133")
    _133(133),

    @Json(name = "134")
    _134(134),

    @Json(name = "135")
    _135(135),

    @Json(name = "136")
    _136(136),

    @Json(name = "137")
    _137(137),

    @Json(name = "138")
    _138(138),

    @Json(name = "139")
    _139(139),

    @Json(name = "140")
    _140(140),

    @Json(name = "141")
    _141(141),

    @Json(name = "142")
    _142(142),

    @Json(name = "143")
    _143(143),

    @Json(name = "144")
    _144(144),

    @Json(name = "145")
    _145(145),

    @Json(name = "146")
    _146(146);

    /**
     * Override toString() to avoid using the enum variable name as the value, and instead use
     * the actual value defined in the API spec file.
     *
     * This solves a problem when the variable name and its value are different, and ensures that
     * the client sends the correct enum values to the server always.
     */
    override fun toString(): String = value.toString()

    companion object {
        /**
         * Converts the provided [data] to a [String] on success, null otherwise.
         */
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is PrimType) "$data" else null

        /**
         * Returns a valid [PrimType] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): PrimType? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}


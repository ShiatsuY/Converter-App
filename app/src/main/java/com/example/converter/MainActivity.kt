package com.example.converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val hiraganaMap = mapOf(
        " " to " ", "a" to "あ", "i" to "い", "u" to "う", "e" to "え", "o" to "お", "ka" to "か", "ga" to "が", "ki" to "き", "gi" to "ぎ",
        "ku" to "く", "gu" to "ぐ", "ke" to "け", "ge" to "げ", "ko" to "こ", "go" to "ご", "sa" to "さ", "za" to "ざ", "shi" to "し", "ji" to "じ",
        "su" to "す", "zu" to "ず", "se" to "せ", "ze" to "ぜ", "so" to "そ", "zo" to "ぞ", "ta" to "た", "da" to "だ", "chi" to "ち", "di" to "ぢ",
        "tsu" to "つ", "du" to "づ", "te" to "て", "de" to "で", "to" to "と", "do" to "ど", "na" to "な", "ni" to "に", "nu" to "ぬ", "ne" to "ね",
        "no" to "の", "ha" to "は", "ba" to "ば", "pa" to "ぱ", "hi" to "ひ", "bi" to "び", "pi" to "ぴ", "fu" to "ふ", "bu" to "ぶ", "pu" to "ぷ",
        "he" to "へ", "be" to "べ", "pe" to "ぺ", "ho" to "ほ", "bo" to "ぼ", "po" to "ぽ", "ma" to "ま", "mi" to "み", "mu" to "む", "me" to "め",
        "mo" to "も", "ya" to "や", "yu" to "ゆ", "yo" to "よ", "ra" to "ら", "ri" to "り", "ru" to "る", "re" to "れ", "ro" to "ろ", "wa" to "わ",
        "wo" to "を", "n" to "ん", "kya" to "きゃ", "kyu" to "きゅ", "kyo" to "きょ", "sha" to "しゃ", "shu" to "しゅ", "sho" to "しょ", "cha" to "ちゃ",
        "chu" to "ちゅ", "cho" to "ちょ", "nya" to "にゃ", "nyu" to "にゅ", "nyo" to "にょ", "hya" to "ひゃ", "hyu" to "ひゅ", "hyo" to "ひょ", "mya" to "みゃ",
        "myu" to "みゅ", "myo" to "みょ", "rya" to "りゃ", "ryu" to "りゅ", "ryo" to "りょ", "gya" to "ぎゃ", "gyu" to "ぎゅ", "gyo" to "ぎょ", "bya" to "びゃ",
        "byu" to "びゅ", "byo" to "びょ", "pya" to "ぴゃ", "pyu" to "ぴゅ", "pyo" to "ぴょ", "ja" to "じゃ", "ju" to "じゅ", "jo" to "じょ", "small tsu" to "っ")

    val stringMap = hiraganaMap.entries.associate { (k, v) -> v to k }
    val vokale = "aeiou"
    val xs = "ゃゅょ"

    fun convertToHiragana(w: List<String>): String? {
        val head = w[0]
        val tail = w.drop(1)
        return if (w.size == 1) hiraganaMap[head]
        else hiraganaMap[head] + convertToHiragana(tail)
    }

    fun convertToString(w: List<String>): String? {
        val head = w[0]
        val tail = w.drop(1)
        return if (w.size == 1) stringMap[head]
        else stringMap[head] + convertToString(tail)
    }

    fun hiraganaToList(w: String, acc: String): List<String> {
        val head = w[0].toString()
        val tail = w.drop(1)
        return if (w.length == 1) listOf(acc + head)
        else if (w.length == 2 && tail[0] in xs) listOf(head + tail[0])
        else if (head == " ") listOf(" ") + hiraganaToList(tail, "")
        else listOf(head) + hiraganaToList(tail, acc)
    }

    fun stringToList(w: String, acc: String): List<String> {
        val head = w[0].toString()
        val tail = w.drop(1)
        return if (w.length == 1) listOf(acc + head)
        else if (head == " ") listOf(" ") + stringToList(tail, "")
        else if (head in vokale) listOf(acc + head) + stringToList(tail, "")
        else if (tail[0] !in vokale && head == "n" ) listOf("n") + stringToList(tail, "")
        else if (head !in vokale && tail[0] !in vokale && head == tail[0].toString()) listOf("small tsu") + stringToList(tail, "")
        else stringToList(tail, acc + head)
    }

    fun isRomaji(w: String): Boolean {
        var romaji = true
        for (c in w) {
            if (c !in 'a' .. 'z' &&  c  !== ' '  ) romaji = false
        }
        return romaji
    }

    fun translate(view: View){
        val w = input.text.toString().toLowerCase()
        output.text = if (isRomaji(w)) convertToHiragana(stringToList(w, "")).toString()
        else convertToString(hiraganaToList(w, "")).toString()

    }

    fun delete(view: View){
        input.text.clear()
        output.text = ""
    }



}
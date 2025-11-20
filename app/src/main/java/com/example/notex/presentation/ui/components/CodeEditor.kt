package com.example.notex.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
//TODO Code editor
fun CodeEditor(
    code: MutableState<String>,
    onCodeChange: (String) -> Unit,
    language: MutableState<String>,
) {
    val lines = code.value.split("\n") // Split code into lines

    Row(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        // Line Numbers
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            lines.indices.forEach { index ->
                Text(
                    text = (index + 1).toString(),
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        // Code Editor with Syntax Highlighting
        SelectionContainer {
            BasicTextField(
                value = code.value,
                onValueChange = onCodeChange,
                textStyle = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),

                visualTransformation = { text ->
                    highlightSyntax(
                        text.text,
                        language.value
                    )
                } // Multi-language support
            )
        }
    }
}

fun highlightSyntax(text: String, language: String): TransformedText {
    val keywordColor = Color(0xFFFFD700) // Gold (for keywords)
    val stringColor = Color(0xFF98FB98)  // Light Green (for strings)
    val commentColor = Color(0xFF808080) // Gray (for comments)
    val functionColor = Color(0xFF87CEFA) // Light Blue (for functions)
    val bracketColor = Color(0xFFFF4500) // Orange-Red (for brackets)
    val insideParenColor = Color(0xFF7FFFD4) // Aquamarine (for text inside ())
    val defaultTextColor = Color.White // White color for all other words

    // Define language-specific keywords
    val keywords = when (language.lowercase()) {
        "kotlin", "java" -> listOf("fun", "val", "var", "if", "else", "for", "while", "return", "class", "import", "package", "override")
        "python" -> listOf("def", "return", "if", "else", "elif", "for", "while", "import", "class", "try", "except")
        "javascript" -> listOf("function", "var", "let", "const", "if", "else", "for", "while", "return", "import", "export")
        "c++", "c" -> listOf("int", "float", "double", "char", "if", "else", "for", "while", "return", "include", "class", "struct")
        else -> listOf()
    }

    val builder = AnnotatedString.Builder().apply {
        // Set the entire text to default white color first
        withStyle(SpanStyle(color = defaultTextColor)) {
            append(text)
        }
    }

    // Match keywords
    val keywordRegex = "\\b(${keywords.joinToString("|")})\\b".toRegex()
    keywordRegex.findAll(text).forEach {
        builder.addStyle(SpanStyle(color = keywordColor), it.range.first, it.range.last + 1)
    }

    // Match strings (anything inside quotes)
    "\"(.*?)\"".toRegex().findAll(text).forEach {
        builder.addStyle(SpanStyle(color = stringColor), it.range.first, it.range.last + 1)
    }

    // Match functions (text followed by '(')
    "\\b\\w+\\b(?=\\()".toRegex().findAll(text).forEach {
        builder.addStyle(SpanStyle(color = functionColor), it.range.first, it.range.last + 1)
    }

    // Match brackets: (), {}, []
    "[(){}\\[\\]]".toRegex().findAll(text).forEach {
        builder.addStyle(SpanStyle(color = bracketColor), it.range.first, it.range.last + 1)
    }

    // Match inside parentheses `(...)`
    "\\(([^)]*)\\)".toRegex().findAll(text).forEach { matchResult ->
        val start = matchResult.range.first + 1 // Inside `(`
        val end = matchResult.range.last        // Inside `)`
        if (start < end) {
            builder.addStyle(SpanStyle(color = insideParenColor), start, end)
        }
    }

    // Match single-line comments
    when (language.lowercase()) {
        "python" -> "#.*".toRegex().find(text)?.let {
            builder.addStyle(SpanStyle(color = commentColor), it.range.first, it.range.last + 1)
        }
        "c", "c++", "java", "javascript", "kotlin" -> "//.*".toRegex().find(text)?.let {
            builder.addStyle(SpanStyle(color = commentColor), it.range.first, it.range.last + 1)
        }
    }

    return TransformedText(builder.toAnnotatedString(), OffsetMapping.Identity)
}
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable

fun CodeEditorPreview() {
    var code = remember { mutableStateOf("fun main(Note) {\n    println(\"Hello, World!\")\n}") }
    CodeEditor(code = code, onCodeChange = { code.value = it }, language = mutableStateOf("java"))
}

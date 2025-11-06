import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myapplication.RoomDb.Note
import com.example.myapplication.RoomDb.NoteType
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.events.Event
import com.itextpdf.kernel.events.IEventHandler
import com.itextpdf.kernel.events.PdfDocumentEvent
import com.itextpdf.kernel.font.PdfFont
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter





fun createPdfWithHeaderFooter(context: Context, fileName: String, note: Note): File? {
    val file = File(context.getExternalFilesDir(null), fileName)
    val writer = PdfWriter(file)
    val pdfDoc = PdfDocument(writer)
    val document = Document(pdfDoc)

    // Add the custom header/footer with dynamic title
    pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, CustomHeaderFooter(note.title))

    // Modify the same `document` instead of returning a new one
    when (note.type) {
        NoteType.CODE -> codeNoteWriter(document, note)
        NoteType.MIND_MAP -> mindMapNoteWriter(document, note)
        NoteType.TASK_MANAGEMENT -> taskManagementNoteWriter(document, note)
        NoteType.TEXT_NOTE -> textNoteWriter(document, note)
        null -> document.add(Paragraph("No content available"))
    }

    document.close()
    return if (file.exists()) file else null

}

// ✅ Pass the same `Document` and `Note` object
 fun codeNoteWriter(document: Document, note: Note) {
    document.add(Paragraph("Code Note").setBold())
    note.codeBlocks?.forEach { codeBlock ->
         document.add(Paragraph("Description: ${codeBlock.description}"))
         document.add(Paragraph(codeBlock.code).setBackgroundColor(DeviceRgb(230, 230, 230)))
         document.add(Paragraph("Language: ${codeBlock.language}"))
         document.add(Paragraph("\n")) // Spacer
    }
}

fun mindMapNoteWriter(document: Document, note: Note) {
    document.add(Paragraph("Mind Map Note").setBold())
    document.add(Paragraph(note.content ?: "No description provided"))
}

fun taskManagementNoteWriter(document: Document, note: Note) {
    document.add(Paragraph("Task Management Note").setBold())
    document.add(Paragraph(note.content ?: "No tasks provided"))
}

fun textNoteWriter(document: Document, note: Note) {
    document.add(Paragraph("Text Note").setBold())
    
    // Split content by lines to preserve formatting
    val lines = note.content.split("\n")
    
    // If line numbers were enabled for this note, include them in the PDF
    if (note.lineNumbers) {
        lines.forEachIndexed { index, line ->
            document.add(
                Paragraph("${index + 1}. $line")
                    .setFontSize(11f)
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
            )
        }
    } else {
        // Just add the content without line numbers
        document.add(
            Paragraph(note.content)
                .setFontSize(11f)
                .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
        )
    }
}

// CustomHeaderFooter now takes a title parameter
class CustomHeaderFooter(private val title: String) : IEventHandler {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun handleEvent(event: Event?) {
        val pdfEvent = event as PdfDocumentEvent
        val pdfDoc = pdfEvent.document
        val page = pdfEvent.page
        val width = page.pageSize.width
        val height = page.pageSize.height

        val font = PdfFontFactory.createFont()
        val headerColor = DeviceRgb(0, 0, 139) // Dark Blue
        val footerColor = DeviceRgb(255, 0, 0) // Red
        val pageNumber = pdfDoc.getPageNumber(page)

        val pdfCanvas = PdfCanvas(page.newContentStreamBefore(), page.resources, pdfDoc)
        val doc = Document(pdfDoc)
        val bold: PdfFont =PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)
        // HEADER: Dynamic Title
        val header = Paragraph(title)
            .setFont(font)
            .setFontSize(14f)
            .setFontColor(headerColor)
            .setTextAlignment(TextAlignment.CENTER)

        doc.showTextAligned(header, width / 2, height - 30, pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.TOP, 0f)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDateTime = LocalDateTime.now().format(formatter)
        // FOOTER: Page Number
        val footer = Paragraph( "Date: $formattedDateTime")
            .setFont(bold)

            .setFontSize(8f)
            .setFontColor(footerColor)
            .setTextAlignment(TextAlignment.CENTER)

        doc.showTextAligned(footer, width / 2, 30f, pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0f)

        pdfCanvas.release()
    }
}

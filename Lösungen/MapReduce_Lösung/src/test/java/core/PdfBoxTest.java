package core;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfBoxTest {
    @Test
    public void textStripperPDF_TextOnly() throws IOException {
        PDDocument pdDocument = null;
        PDFTextStripper textStripper = null;
        pdDocument = PDDocument.load(new File("src/test/resources/TestFileTextOnly.pdf"));
        textStripper = new PDFTextStripper();
        String text = textStripper.getText(pdDocument).replaceAll("\\s+", " ");
        assertEquals("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam ", text);
    }

    @Test
    public void textStripperPDF_WithImage() throws IOException {
        PDDocument pdDocument = null;
        PDFTextStripper textStripper = null;
        pdDocument = PDDocument.load(new File("src/test/resources/TestFileWithImage.pdf"));
        textStripper = new PDFTextStripper();
        String text = textStripper.getText(pdDocument).replaceAll("\\s+", " ");
        assertEquals("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam ", text);
    }

    @Test
    public void textStripperPDF_WithTable() throws IOException {
        PDDocument pdDocument = null;
        PDFTextStripper textStripper = null;
        pdDocument = PDDocument.load(new File("src/test/resources/TestFileWithTable.pdf"));
        textStripper = new PDFTextStripper();
        String text = textStripper.getText(pdDocument).replaceAll("\\s+", " ");
        assertEquals("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam Name Age City Gender University lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem ", text);
    }

}
package core;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


public class PDFFileRecordReader extends RecordReader<Text, Text> {
    static boolean isSet = false;
    private Text key = new Text();
    private Text value = new Text();
    private String fileContentAsString = null;
    private PDDocument pdDocument = null;
    private PDFTextStripper textStripper = null;

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context)
            throws IOException {
        FileSplit fileSplit = (FileSplit) split;
        final Path file = fileSplit.getPath();
        Configuration conf = context.getConfiguration();
        FileSystem fileSystem = file.getFileSystem(conf);
        FSDataInputStream fsDataInputStream = fileSystem.open(fileSplit.getPath());
        if (fsDataInputStream != null) {
            pdDocument = PDDocument.load(fsDataInputStream);
            if (pdDocument != null) {
                textStripper = new PDFTextStripper();
                fileContentAsString = textStripper.getText(pdDocument);
            }
        }
    }

    // False ends the reading process
    @Override
    public boolean nextKeyValue() {
        if (!(isSet)) {
            key.set("writtenkey");
            value.set(fileContentAsString);
            isSet = true;
            return true;
        } else {
            // All lines are read? -> end
            key = null;
            value = null;
            return false;
        }
    }

    @Override
    public Text getCurrentKey() {
        return key;
    }

    @Override
    public Text getCurrentValue() {
        return value;
    }

    @Override
    public float getProgress() {
        return 0;
    }

    @Override
    public void close() throws IOException {
        // If done close the doc
        if (pdDocument != null) {
            pdDocument.close();
        }
    }
}

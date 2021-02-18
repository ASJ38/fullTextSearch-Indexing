package core;


import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class ElasticsearchMapper extends Mapper<Text, Text,Text, MapWritable> {

    @Override
    public void map(Text key, Text value, Context context)
            throws IOException, InterruptedException {
        MapWritable map = new MapWritable();
        map.put(new Text("file_content"), new Text(value));
        map.put(new Text("source"), new Text((((FileSplit) context.getInputSplit()).getPath().toString())));
        map.put(new Text("filename"), new Text(((FileSplit) context.getInputSplit()).getPath().getName()));
        context.write(new Text(), map);
    }
}

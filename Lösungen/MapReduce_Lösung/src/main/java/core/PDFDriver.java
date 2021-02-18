package core;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.elasticsearch.hadoop.mr.EsOutputFormat;

import java.io.IOException;

public class PDFDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.setBoolean("mapred.map.tasks.speculative.execution", false);
        conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);
        conf.set(" es.net.http.auth.user", args[0]);
        conf.set("es.net.http.auth.pass", args[1]);
        conf.set("es.nodes", args[2]);
        conf.set("es.resource", args[3]);
        Path input = new Path(args[4]);
        Job job = new Job(conf, "Indexing into Elastic search.");
        FileInputFormat.addInputPath(job, input);
        job.setInputFormatClass(PDFInputFormat.class);
        job.setJarByClass(PDFDriver.class);
        job.setOutputFormatClass(EsOutputFormat.class);
        job.setMapOutputValueClass(MapWritable.class);
        job.setMapperClass(ElasticsearchMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.waitForCompletion(true);
    }
}

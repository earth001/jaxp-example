package com.proitc.jmh;

import com.proitc.dom4j.Dom4jTransformer;
import com.proitc.jaxp.JaxpTransformer;
import com.proitc.joox.JooxTransformer;
import org.dom4j.DocumentException;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class AttributeBenchMark {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
          .include(AttributeBenchMark.class.getSimpleName())
          .forks(1)
          .build();
        new Runner(opt).run();
    }

    @Benchmark
    public String dom4jBenchmark() throws DocumentException, TransformerException, SAXException {
        String path = getClass()
          .getResource("/xml/attribute.xml")
          .toString();
        Dom4jTransformer transformer = new Dom4jTransformer(path, true);
        String attribute = "customer";
        String oldValue = "true";
        String newValue = "false";

        return transformer.modifyAttribute(attribute, oldValue, newValue);
    }

    @Benchmark
    public String jooxBenchmark() throws IOException, SAXException {
        String path = getClass()
          .getResource("/xml/attribute.xml")
          .toString();
        JooxTransformer transformer = new JooxTransformer(path);
        String attribute = "customer";
        String oldValue = "true";
        String newValue = "false";

        return transformer.modifyAttribute(attribute, oldValue, newValue);
    }

    @Benchmark
    public String jaxpBenchmark() throws TransformerException, ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        String path = getClass()
          .getResource("/xml/attribute.xml")
          .toString();
        JaxpTransformer transformer = new JaxpTransformer(path);
        String attribute = "customer";
        String oldValue = "true";
        String newValue = "false";

        return transformer.modifyAttribute(attribute, oldValue, newValue);
    }
}

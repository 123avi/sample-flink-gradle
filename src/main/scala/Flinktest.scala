import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.ProcessAllWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

object Flinktest extends App {
  private val env = StreamExecutionEnvironment.getExecutionEnvironment
  env.fromElements("A", "B","c")
    .windowAll(TumblingEventTimeWindows.of(Time.seconds(5)))
    .process{new ProcessAllWindowFunction[String, List[String], TimeWindow] {
      override def process(context: Context, elements: Iterable[String], out: Collector[List[String]]): Unit = {
        out.collect(elements.toList)
      }
    }
    }
    .print()

  env.execute("Sample")
}

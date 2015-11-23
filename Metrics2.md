_<font color='red'>Genson website has moved to <a href='http://owlike.github.io/genson/'>http://owlike.github.io/genson/</a> and the code is hosted on GitHub at <a href='https://github.com/owlike/genson'>https://github.com/owlike/genson</a></font>_

# Benchmark & Metrics #

> All the following measures were made with Genson 0.93, Jackson 1.9.9 and Gson 2.2.3 on a Intel core i7 laptop with 2GHz and 4 Gio of RAM.
> The following benchmarks take into account the latest versions of Genson and Jackson but also the recent optimizations added to Gson  improving their deserialization performances.

> ## Performance benchmarks ##

> This first benchmark compares Genson to others known librairies using [jvm-serializers](https://github.com/eishay/jvm-serializers/wiki/) .
> The dataset can be found [here](https://github.com/eishay/jvm-serializers/blob/kannan/tpc/data/media.3.cks).

> <img src='https://chart.googleapis.com/chart?chtt=total+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x150&chd=t:15020,19363,27431,57752,162714,225332&chds=0,247866.06735000003&chxt=y&chxl=0:|json%2Fjson-lib-databind|json%2Fflexjson%2Fdatabind|json%2Fsvenson-databind|json%2Fgoogle-gson%2Fdatabind|json%2Fgenson%2Fdatabind|json%2Fjackson%2Fdatabind-strings&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=10,0,10&nonsense=aaa.png' />
> <img src='https://chart.googleapis.com/chart?chtt=ser+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x150&chd=t:7265,10850,17885,21964,42852,53675&chds=0,59042.52695000001&chxt=y&chxl=0:|json%2Fjson-lib-databind|json%2Fflexjson%2Fdatabind|json%2Fsvenson-databind|json%2Fgoogle-gson%2Fdatabind|json%2Fgenson%2Fdatabind|json%2Fjackson%2Fdatabind-strings&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=10,0,10&nonsense=aaa.png' />
> <img src='https://chart.googleapis.com/chart?chtt=deser+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x150&chd=t:7755,8513,9546,35787,119862,171657&chds=0,188823.5404&chxt=y&chxl=0:|json%2Fjson-lib-databind|json%2Fflexjson%2Fdatabind|json%2Fsvenson-databind|json%2Fgoogle-gson%2Fdatabind|json%2Fgenson%2Fdatabind|json%2Fjackson%2Fdatabind-strings&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=10,0,10&nonsense=aaa.png' />


> #### Benchmarking methodology ####
> The following benchmarks were run in the same jvm. Before starting to measure performances, each library has been warmed up. To limit memory usage impact, an explicit call to the garbage collector has been made, followed by sleep of the running thread for some seconds between each library bench.

> Here is a second benchmark using Gson benchmarks data (located in their metrics project). This chart represents serialization and deserialization average time of [LONG\_READER](http://code.google.com/p/genson/source/browse/src/test/resources/READER_LONG.json) data in milliseconds with 5000 iterations and 50 warmup iterations. The documents weights 189Ko. Complete results in their numeric reprensentation can be found [here](http://code.google.com/p/genson/source/browse/bench_results/benchmark_gson_metrics.txt). Serialization source code is located [here](http://code.google.com/p/genson/source/browse/src/test/java/com/owlike/genson/SerializationBenchmark.java) and deserialization [here](http://code.google.com/p/genson/source/browse/src/test/java/com/owlike/genson/DeserializeBenchmark.java).

> <img src='http://genson.googlecode.com/files/gson_metrics_bench.png' />


> The last benchmark goal is to measure performances of use cases where you have got a lot of beans (so reflection will be used), big numbers, many lists, arrays and maps (small and big). Note that we compare two versions of Genson, the first one uses a custom algorithm allowing to parse doubles really fast, but with a small precision loss, the other one uses standard Double.parse. In most cases using the custom algorithm should be fine.

> <img src='http://genson.googlecode.com/files/genson_fictive_bench.png' />

> When looking at all those benchmarks we can see that Genson and Jackson performances are relatively close and Gson latest optimizations improved nicely its performances.
> Performances are important, but at that level other things may be more important, such as library design, ease to use and ease to develop and intergrate new features by yourself.

---

> ## Artifact size ##
> Last measure that can be very important for mobile developers is the jar size. In fact Gson has the smallest archive size but Genson is faster and weights only 2Ko more (Jackson is 5 times heavier)!

> <img src='http://genson.googlecode.com/files/Jarsizeinko.JPG' />
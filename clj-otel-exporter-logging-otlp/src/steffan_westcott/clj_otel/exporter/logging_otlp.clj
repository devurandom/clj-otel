(ns steffan-westcott.clj-otel.exporter.logging-otlp
  "Exporters that log telemetry data in OTLP JSON format using
   `java.util.logging`. Intended for debugging only."
  (:import (io.opentelemetry.exporter.logging.otlp OtlpJsonLoggingMetricExporter
                                                   OtlpJsonLoggingSpanExporter)
           (io.opentelemetry.sdk.metrics.data AggregationTemporality)
           (io.opentelemetry.sdk.metrics.export MetricExporter)
           (io.opentelemetry.sdk.trace.export SpanExporter)))

(defn span-exporter
  "Returns a span exporter that logs every span in OTLP JSON format using
   `java.util.logging`."
  ^SpanExporter []
  (OtlpJsonLoggingSpanExporter/create))

(defn metric-exporter
  "Returns a metric exporter that logs every metric in OTLP JSON format using
   `java.util.logging`. May take an option map as follows:

   | key                      | description |
   |--------------------------|-------------|
   |`:aggregation-temporality`| ^AggregationTemporality Time period over which metrics should be aggregated (default: `CUMULATIVE`)."
  (^MetricExporter []
   (OtlpJsonLoggingMetricExporter/create))
  (^MetricExporter
   [{:keys [aggregation-temporality]
     :or   {aggregation-temporality AggregationTemporality/CUMULATIVE}}]
   (OtlpJsonLoggingMetricExporter/create aggregation-temporality)))

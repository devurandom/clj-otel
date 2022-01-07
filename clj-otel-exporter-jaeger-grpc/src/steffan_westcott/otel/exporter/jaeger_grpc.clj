(ns steffan-westcott.otel.exporter.jaeger-grpc
  "Span data exporter to Jaeger via gRPC."
  (:require [steffan-westcott.otel.util :as util])
  (:import (io.opentelemetry.exporter.jaeger JaegerGrpcSpanExporter)))

(defn span-exporter
  "Returns a span exporter that exports to Jaeger via gRPC, using Jaeger's
  protobuf model. May take an option map as follows:

  | key                       | description |
  |---------------------------|-------------|
  |`:endpoint`                | Jaeger endpoint, used with the default `:channel` (default: `\"http://localhost:14250\"`).
  |`:trusted-certificates-pem`| `^bytes` X.509 certificate chain in PEM format, used with the default channel (default: system default trusted certificates).
  |`:timeout`                 | Maximum time to wait for export of a batch of spans.  Value is either a `Duration` or a vector `[amount ^TimeUnit unit]` (default: 10s).
  |`:channel`                 | `ManagedChannel` instance to use for communication with the backend (default: `ManagedChannel` instance configured to use `:endpoint`)."
  ([]
   (span-exporter {}))
  ([{:keys [endpoint trusted-certificates-pem timeout channel]}]
   (let [builder (cond-> (JaegerGrpcSpanExporter/builder)
                         endpoint (.setEndpoint endpoint)
                         trusted-certificates-pem (.setTrustedCertificates trusted-certificates-pem)
                         timeout (.setTimeout (util/duration timeout))
                         channel (.setChannel channel))]
     (.build builder))))
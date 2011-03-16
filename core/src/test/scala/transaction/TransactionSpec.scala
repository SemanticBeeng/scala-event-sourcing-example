package com.zilverline.es2
package transaction

import eventstore._

class TransactionSpec extends org.specs2.mutable.SpecificationWithJUnit {

  trait Context extends org.specs2.execute.Success {
    val eventStore = new MemoryEventStore
    val emptyUnitOfWork = UnitOfWork()

    val Source = newIdentifier
  }

  "track event source" in new Context {
    val result = trackEventSource(Source, 3, "three").apply(emptyUnitOfWork)

    result.uow.eventSources(Source) must beEqualTo(EventSource(Source, 3, "three", IndexedSeq.empty))
  }

  "track current event source revision" in new Context {
    val result = trackEventSource(Source, 1, "original")
      .andThen(modifyEventSource(Source, ExampleEvent("example")){ _ => "example" })
      .apply(emptyUnitOfWork)

    result.uow.eventSources(Source) must beEqualTo(EventSource(Source, 1, "example", IndexedSeq(ExampleEvent("example"))))
  }
}

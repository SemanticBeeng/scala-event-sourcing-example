package com.zilverline.es2

import behavior._

package object domain {
  def load[AR <: AggregateRoot](source: Identifier)(implicit factory: AggregateFactory[AR]): Behavior[AR] = Behavior {
    uow =>
      val events = uow.eventStore.load(source)
      Accepted(uow, factory.loadFromHistory[AR](events))
  }
}
package org.pmp.budgeto.domain

import java.util.UUID

class IdGenerator {
  def next(): String = UUID.randomUUID().toString
}

package org.pmp

package object budgeto {

  class IdGeneratorFixed(var value: String) {
    def next(): String = value
  }

}

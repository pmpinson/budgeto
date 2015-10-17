package org.pmp.budgeto.domain.account

import org.joda.time.DateTime

case class Account(id: String, label: String, note: String, initialBalance: Double, balance: Double, creationDate: DateTime)

case class AccountOperation(id: String, label: String, amount: Double, date: DateTime) extends Ordered[AccountOperation] {
  override def compare(that: AccountOperation): Int = date.compareTo(that.date)
}




















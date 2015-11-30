package org.pmp.budgeto.domain.account

import org.joda.time.DateTime

case class Account(id: String, note: String, initialBalance: Double, balance: Double, creationDate: DateTime)

case class AccountOperation(id: String, label: String, amount: Double, date: DateTime) extends Ordered[AccountOperation] {
  override def compare(that: AccountOperation): Int = date.compareTo(that.date)
}

sealed trait AccountState

object AccountStateOpened extends AccountState

object AccountStateClosed extends AccountState

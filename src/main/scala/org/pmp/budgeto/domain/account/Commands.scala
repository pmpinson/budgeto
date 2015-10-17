package org.pmp.budgeto.domain.account

case class CreateAccount(label: String, note: String, initialBalance: Double = 0)

case class CloseAccount(accountId: String)

case class CreateAccountOperation(accountId: String, label: String, n: Double)
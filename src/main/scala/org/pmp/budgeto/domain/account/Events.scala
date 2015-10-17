package org.pmp.budgeto.domain.account

case class AccountCreated(account: Account)

case class AccountClosed(account: Account)

case class AccountOperationCreated(accountId: String, operation: AccountOperation)
package org.pmp.budgeto.domain.account

case class CreateAccountSuccess(account: Account)

case class CloseAccountSuccess(account: Account)

case class CreateAccountOperationSuccess(accountId: String, operation: AccountOperation)
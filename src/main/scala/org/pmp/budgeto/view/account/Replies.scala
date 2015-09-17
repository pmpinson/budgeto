package org.pmp.budgeto.view.account

import org.pmp.budgeto.domain.account._

import scala.collection.SortedSet

case class PrintAccountsSuccess(accounts: List[Account])

case class PrintClosedAccountsSuccess(accounts: List[Account])

case class PrintAccountOperationsSuccess(operations: SortedSet[AccountOperation])
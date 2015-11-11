package org.pmp.budgeto.domain.account

import org.joda.time.DateTime

case class Account(id: String, label: String, note: String, initialBalance: Double, balance: Double, creationDate: DateTime)

package com.simplebank.bank.usecases.ports;

import java.util.List;

public record AuthenticatedUserAccountDTOResponse(Long id, Double balance,
                                                  List<TransactionDTO> payerTransactions,
                                                  List<TransactionDTO> payeeTransactions)
{
}

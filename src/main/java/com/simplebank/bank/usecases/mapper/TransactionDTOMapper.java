package com.simplebank.bank.usecases.mapper;

import com.simplebank.bank.domain.models.Transaction.Transaction;
import com.simplebank.bank.usecases.ports.TransactionDTO;
import java.util.List;

public class TransactionDTOMapper
{
  public List<TransactionDTO> toResponse(List<Transaction> transactions)
  {
    return transactions.stream().map(
        t -> new TransactionDTO(t.getId(), t.getAmount(), t.getPayer().getUser().getEmail(),
            t.getPayee().getUser().getEmail())).toList();
  }
}

package com.simplebank.bank.infra.auth;

import com.simplebank.bank.usecases.ports.TransferAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

@Slf4j
public class TransferAuthServiceInRest implements TransferAuthService
{
  private final String url;
  private final RestClient client;

  public TransferAuthServiceInRest(String url)
  {
    this.url = url;
    this.client = RestClient.create();
  }

  @Override
  public boolean authorize()
  {
    try
    {
      var response = client.get().uri(this.url).retrieve().body(Response.class);

      log.info("service auth response: {}", response);

      if (response == null)
      {
        return false;
      }

      return response.data.authorization;
    } catch (Exception e)
    {
      log.error("service auth exception: {}", e.toString());

      return false;
    }
  }


  private record Data(boolean authorization)
  {
  }

  private record Response(String status, Data data)
  {
  }
}

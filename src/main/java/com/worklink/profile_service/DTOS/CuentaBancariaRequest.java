package com.worklink.profile_service.DTOS;

import jakarta.validation.constraints.NotBlank;

public class CuentaBancariaRequest {

    @NotBlank(message = "El titular es obligatorio")
    private String titular;

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String tipoCuenta;    // "AHORROS", "CORRIENTE"

    @NotBlank(message = "El banco es obligatorio")
    private String banco;

    @NotBlank(message = "El documento es obligatorio")
    private String documento;

    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
}

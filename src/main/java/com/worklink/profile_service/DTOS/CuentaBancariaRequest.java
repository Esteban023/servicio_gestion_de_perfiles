package com.worklink.profile_service.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CuentaBancariaRequest {

    @NotBlank(message = "El titular es obligatorio")
    @Size(min = 3, max = 100, message = "El titular debe tener entre 3 y 100 caracteres")
    private String titular;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Pattern(regexp = "\\d{11,20}", message = "El número de cuenta debe contener solo dígitos y tener entre 11 y 20 caracteres")
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @Pattern(regexp = "(?i)^(AHORROS|CORRIENTE)$", message = "El tipo de cuenta debe ser AHORROS o CORRIENTE")
    private String tipoCuenta;    // "AHORROS", "CORRIENTE"

    @NotBlank(message = "El banco es obligatorio")
    @Size(min = 3, max = 50, message = "El banco debe tener entre 3 y 50 caracteres")
    private String banco;

    @NotBlank(message = "El documento es obligatorio")
    @Pattern(regexp = "\\d{6,12}", message = "El documento debe contener solo dígitos y tener entre 6 y 12 caracteres")
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

package com.todeb.bkolay.creditapplication.model.entity;

public enum CreditLimit {
    /**
     * Kredi skoru 500’ün altında ise kullanıcı reddedilir. (Kredi sonucu: Red)
     * - Kredi skoru 500 puan ile 1000 puan arasında ise ve aylık geliri 5000 TL’nin altında ise
     * kullanıcının kredi başvurusu onaylanır ve kullanıcıya 10.000 TL limit atanır. (Kredi
     * Sonucu: Onay)
     * - Kredi skoru 500 puan ile 1000 puan arasında ise ve aylık geliri 5000 TL’nin üstünde ise
     * kullanıcının kredi başvurusu onaylanır ve kullanıcıya 20.000 TL limit atanır. (Kredi
     * Sonucu: Onay)
     * - Kredi skoru 1000 puana eşit veya üzerinde ise kullanıcıya AYLIK GELİR BİLGİSİ * KREDİ
     * LİMİT ÇARPANI kadar limit atanır. (Kredi Sonucu: Onay)
     */
    LOW(10000.00,5000.00),
    HIGH(20000.00,5000.00),
    SPECIAL(null,null);

    private Double creditLimit;
    private Double salaryLimit;

    CreditLimit(Double creditLimit, Double salaryLimit) {
        this.creditLimit = creditLimit;
        this.salaryLimit = salaryLimit;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getSalaryLimit() {
        return salaryLimit;
    }

    public void setSalaryLimit(Double salaryLimit) {
        this.salaryLimit = salaryLimit;
    }
}

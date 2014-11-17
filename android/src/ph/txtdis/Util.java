package ph.txtdis;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Util {

    public Util() {
    }

    public static String toPeso(BigDecimal number) {
        if (isZero(number))
            return "";
        return new DecimalFormat("₱#,##0.00;(₱#,##0.00)").format(number);
    }

    public static String centToPeso(int number) {
        return toPeso(new BigDecimal(number).divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
    }

    public static String centMilleToPeso(int cent, int mille) {
        return toPeso(new BigDecimal(cent).multiply(new BigDecimal(mille)).divide(new BigDecimal("100000"), 2,
                RoundingMode.HALF_EVEN));
    }

    public static String toQty(BigDecimal number) {
        return new DecimalFormat("#,###.##;(#,###.##)").format(number);
    }

    public static String milleToQty(int number) {
        return toQty(new BigDecimal(number).divide(new BigDecimal("1000"), 2, RoundingMode.HALF_EVEN));
    }

    public static String toVatablePeso(BigDecimal total) {
        return toPeso(getVatable(total));
    }

    public static String toVatPeso(BigDecimal total) {
        return toPeso(total.subtract(getVatable(total)));
    }

    public static BigDecimal getVatable(BigDecimal total) {
        return total.divide(new BigDecimal("1.12"), 2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toPercentage(BigDecimal perCent) {
        return perCent.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
    }

    public static String toPercent(BigDecimal number) {
        if (isZero(number))
            return "";
        return new DecimalFormat("##0.00%;(##0.00%)").format(toPercentage(number));
    }

    public static boolean isZero(BigDecimal number) {
        return number == null || number.compareTo(BigDecimal.ZERO) == 0;
    }

    public static String toId(int id) {
        return id == 0 ? "" : String.valueOf(id);
    }

    public static String toId(long id) {
        return id == 0 ? "" : String.valueOf(id);
    }
}

package com.comcast.algorithms;

/**
 * Created by zzhou200 on 8/2/15.
 */
public class BigInt implements Comparable<BigInt> {
    public static final int MAX_DIGIT_NUM = 1024;
    private int sign;   // 1 - positive, 0 - zero, -1 - negative
    private byte[] digits;
    private int digitCount;

    public BigInt() {
        this.sign = 0;
        this.digits = new byte[MAX_DIGIT_NUM];
        digitCount = 1;
    }

    public BigInt(BigInt another) {
        this.sign = another.getSign();
        this.digits = new byte[MAX_DIGIT_NUM];
        System.arraycopy(another.getDigits(), 0, this.digits, 0, another.getDigitCount());
        digitCount = another.getDigitCount();
    }

    public BigInt(int sign, byte[] digits) {
        this.sign = sign;
        this.digits = new byte[MAX_DIGIT_NUM];
        System.arraycopy(digits, 0, this.digits, 0, digits.length);
        digitCount = digits.length;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public byte[] getDigits() {
        return digits;
    }

    public void setDigits(byte[] digits) {
        System.arraycopy(digits, 0, this.digits, 0, digits.length);
        digitCount = digits.length;
    }

    public byte digitAt(int i) {
        return digits[i];
    }

    public void setDigitAt(byte digit, int i) {
        digits[i] = digit;
    }

    public int getDigitCount() {
        return digitCount;
    }

    public boolean isPositive() {
        return sign > 0;
    }

    public boolean isZero() {
        return sign == 0;
    }

    public boolean isNegative() {
        return sign < 0;
    }

    public BigInt add(BigInt another) {
        return BigInt.add(this, another);
    }
    public BigInt minus(BigInt another) {
        return BigInt.minus(this, another);
    }
    public BigInt multiply(BigInt another) {
        return BigInt.multiply(this, another);
    }

    public static BigInt add(BigInt a, BigInt b) {
        if (a.isZero())
            return new BigInt(b);
        if (b.isZero())
            return new BigInt(a);

        BigInt c = new BigInt();

        if (a.isPositive() && b.isPositive()) {
            c.setSign(1);
            c.setDigits(BigInt.rawAdd(a.getDigits(), a.getDigitCount(), b.getDigits(), b.getDigitCount()));
        } else if (a.isNegative() && b.isNegative()) {
            c.setSign(-1);
            c.setDigits(BigInt.rawAdd(a.getDigits(), a.getDigitCount(), b.getDigits(), b.getDigitCount()));
        } else {
            if (a.absCompareTo(b) == 0)
                return new BigInt();
            else if (a.absCompareTo(b) > 1) {
                c.setSign(a.getSign());
                c.setDigits(BigInt.rawMinus(a.getDigits(), a.getDigitCount(), b.getDigits(), b.getDigitCount()));
            } else {
                c.setSign(b.getSign());
                c.setDigits(BigInt.rawMinus(b.getDigits(), b.getDigitCount(), a.getDigits(), a.getDigitCount()));
            }
        }

        return c;
    }

    public static BigInt minus(BigInt a, BigInt b) {
        BigInt bb = new BigInt(b);
        bb.setSign(b.getSign() * -1);
        return BigInt.add(a, bb);
    }

    public static BigInt multiply(BigInt a, BigInt b) {
        if (a.isZero() || b.isZero())
            return new BigInt();

        BigInt product = BigInt.rawMultiply(a.getDigits(), a.getDigitCount(), b.getDigits(), b.getDigitCount());

        if (a.getSign() != b.getSign())
            product.setSign(-1);

        return product;
    }

    public int absCompareTo(BigInt another) {
        if (digitCount > another.getDigitCount())
            return 1;
        else if (digitCount < another.getDigitCount())
            return -1;

        for (int i= digitCount-1; i>=0; ++i) {
            int dif = digitAt(i) - another.digitAt(i);
            if (dif == 0)
                continue;
            return dif;
        }
        return 0;
    }

    @Override
    public int compareTo(BigInt another) {
        if (sign > another.sign)
            return 1;
        else if (sign < another.sign)
            return -1;
        else if (sign == 1)
            return absCompareTo(another);
        else if (sign == -1)
            return -1 * absCompareTo(another);

        // both sign are 0
        return 0;
    }

    // internal methods

    private static byte[] rawAdd(byte[] a, int aDigitCount, byte[] b, int bDigitCount) {
        int dc = Math.max(aDigitCount, bDigitCount);
        byte[] c = new byte[dc];
        byte carry = 0;
        for (int i = 0; i < dc; ++i) {
            c[i] = (byte) (a[i] + b[i] + carry);
            carry = 0;
            if (c[i] >= 10) {
                carry = 1;
                c[i] -= 10;
            }
        }

        if (carry == 0)
            return c;

        // process carry of the most significant digit
        byte[] cc = new byte[dc + 1];
        System.arraycopy(c, 0, cc, 0, dc);
        cc[dc] = 1;
        return cc;
    }

    private static byte[] rawMinus(byte[] a, int aDigitCount, byte[] b, int bDigitCount) {
        // We made sure a > b to make sure we never have to borrow after the last digit, so we're done
        assert ((aDigitCount >= bDigitCount) || (aDigitCount == bDigitCount && a[aDigitCount] >= b[bDigitCount]));

        byte[] c = new byte[aDigitCount];
        byte borrow = 0;
        for (int i = 0; i < aDigitCount; ++i) {
            c[i] = (byte) (a[i] - b[i] - borrow);
            borrow = 0;
            if (c[i] < 0) {
                borrow = 1; // We'll borrow 10 from the next pair of digits
                c[i] += 10;
            }
        }

        if (c[aDigitCount - 1] != 0)
            return c;

        // remove the 0 at most significant digit
        byte[] cc = new byte[aDigitCount - 1];
        System.arraycopy(c, 0, cc, 0, aDigitCount - 1);
        return cc;
    }

    private static BigInt rawMultiply(byte[] a, int aDigitCount, byte[] b, int bDigitCount) {
        BigInt product = new BigInt();

        for (int i=0; i<bDigitCount; ++i) {
            byte[] partial = rawMultiply(a, aDigitCount, b[i]);
            byte[] shiftedPartial = new byte[partial.length+i];
            System.arraycopy(partial, 0, shiftedPartial, i, partial.length);
            for (int j=0; j<i; ++j)
                shiftedPartial[j] = 0;

            BigInt pp = new BigInt(1, shiftedPartial);
            product = BigInt.add(product, pp);
        }

        return product;
    }

    private static byte[] rawMultiply(byte[] a, int aDigitCount, byte b) {
        assert (b >= 0 && b < 10);
        byte[] c = new byte[aDigitCount];

        byte carry = 0;
        for (int i = 0; i < aDigitCount; ++i) {
            byte d = (byte) (a[i] * b + carry);
            carry = (byte) (d / 10);
            c[i] = (byte) (d % 10);
        }

        if (carry == 0)
            return c;

        // add carry to the most significant digit
        byte[] cc = new byte[aDigitCount+1];
        System.arraycopy(c, 0, cc, 0, aDigitCount);
        cc[aDigitCount] = carry;
        return cc;
    }
}

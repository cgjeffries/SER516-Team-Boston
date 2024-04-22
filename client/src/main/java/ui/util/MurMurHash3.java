package ui.util;

/**
 * A very specific implementation of MurMur3. Unless this function returns a negative number (in
 * which case I did not port it correctly), this should not change! This is a port of the hash taiga
 * uses to compute the default logos for projects and users. You can find the original function
 * here: https://github.com/garycourt/murmurhash-js/blob/master/murmurhash3_gc.js
 */
public class MurMurHash3 {
    public static long hash(String key, int seed) {
        long h1b;

        int k1;
        int remainder = key.length() & 3; // key.length % 4
        int bytes = key.length() - remainder;
        int h1 = seed;
        long c1 = Long.parseLong("CC9E2D51", 16);
        long c2 = Long.parseLong("1B873593", 16);
        int i = 0;

        while (i < bytes) {
            k1 =
                    ((key.charAt(i) & 0xff))
                            | ((key.charAt(++i) & 0xff) << 8)
                            | ((key.charAt(++i) & 0xff) << 16)
                            | ((key.charAt(++i) & 0xff) << 24);

            ++i;

            k1 =
                    (int)
                            (((((k1 & 0xffff) * c1) + ((((k1 >>> 16) * c1) & 0xffff) << 16)))
                                    & 0xffffffff);
            k1 = (k1 << 15) | (k1 >>> 17);
            k1 =
                    (int)
                            (((((k1 & 0xffff) * c2) + ((((k1 >>> 16) * c2) & 0xffff) << 16)))
                                    & 0xffffffff);

            h1 ^= k1;
            h1 = (h1 << 13) | (h1 >>> 19);
            h1b = ((((h1 & 0xffff) * 5) + ((((h1 >>> 16) * 5) & 0xffff) << 16))) & 0xffffffff;
            h1 = (int) (((h1b & 0xffff) + 0x6b64) + ((((h1b >>> 16) + 0xe654) & 0xffff) << 16));
        }

        k1 = 0;

        switch (remainder) {
            case 3:
                k1 ^= (key.charAt(i + 2) & 0xff) << 16;
            case 2:
                k1 ^= (key.charAt(i + 1) & 0xff) << 8;
            case 1:
                k1 ^= (key.charAt(i) & 0xff);

                k1 =
                        (int)
                                ((((k1 & 0xffff) * c1) + ((((k1 >>> 16) * c1) & 0xffff) << 16))
                                        & 0xffffffff);
                k1 = (k1 << 15) | (k1 >>> 17);
                k1 =
                        (int)
                                ((((k1 & 0xffff) * c2) + ((((k1 >>> 16) * c2) & 0xffff) << 16))
                                        & 0xffffffff);
                h1 ^= k1;
        }

        h1 ^= key.length();

        h1 ^= h1 >>> 16;
        h1 =
                (((h1 & 0xffff) * 0x85ebca6b) + ((((h1 >>> 16) * 0x85ebca6b) & 0xffff) << 16))
                        & 0xffffffff;
        h1 ^= h1 >>> 13;
        h1 =
                ((((h1 & 0xffff) * 0xc2b2ae35) + ((((h1 >>> 16) * 0xc2b2ae35) & 0xffff) << 16)))
                        & 0xffffffff;
        h1 ^= h1 >>> 16;

        return Long.parseLong(Integer.toUnsignedString(h1));
    }
}
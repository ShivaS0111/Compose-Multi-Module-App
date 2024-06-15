package com.example.test_movie_app;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

public class RMGRSACertificates {

    static public class RMGRSANonProdCertificates {
        static String cert1 = "-----BEGIN CERTIFICATE-----\n" +
                "        MIIHujCCBaKgAwIBAgIJAO32DGevBH5FMA0GCSqGSIb3DQEBCwUAMIG5MQswCQYD\n" +
                "        VQQGEwJHSTESMBAGA1UECBMJR2licmFsdGFyMRIwEAYDVQQHEwlHaWJyYWx0YXIx\n" +
                "        HTAbBgNVBAoTFFBhcnR5R2FtaW5nIFNlcnZpY2VzMR0wGwYDVQQLExRJbmZvcm1h\n" +
                "        dGlvbiBTZWN1cml0eTEcMBoGA1UEAxMTUGFydHlHYW1pbmcgUm9vdCBDQTEmMCQG\n" +
                "        CSqGSIb3DQEJARYXaW5mb3NlY0BwYXJ0eWdhbWluZy5jb20wHhcNMTUwNDE2MTEw\n" +
                "        NzAwWhcNMjUwNDE2MTEwNzAwWjCB1DELMAkGA1UEBhMCR0kxEjAQBgNVBAgTCUdp\n" +
                "        YnJhbHRhcjESMBAGA1UEBxMJR2licmFsdGFyMS0wKwYDVQQKEyRid2luLnBhcnR5\n" +
                "        IGRpZ2l0YWwgZW50ZXJ0YWlubWVudCBwbGMxHjAcBgNVBAsTFVRlY2hub2xvZ3kg\n" +
                "        R292ZXJuYW5jZTEbMBkGA1UEAxMSYndpbi5wYXJ0eSBUZXN0IENBMTEwLwYJKoZI\n" +
                "        hvcNAQkBFiJ0ZWNobm9sb2d5Z292ZXJuYW5jZUBid2lucGFydHkuY29tMIICIjAN\n" +
                "        BgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAomozkkVbeId5X7Q6QYNncwdl29GA\n" +
                "        KDEhYRc9xXbL/n6djbqP0K869+jBJMrpk7INhfUJuthGYrQYpzkAvin2hxsLX4ww\n" +
                "        d9qspRsvfdkfbyjnkNXvDmxQNA6bOH2RAri5djW3YML9NjirSMSELTmwsUJRl3aW\n" +
                "        trOd75WmhBErMW9omDbdy+5RmYofKgdzG6aeJ+wmLoTym1o/Xnb7P5qk5z2LoRW7\n" +
                "        +a58kWbsMC15h645j0slPf2OdFKUqCquE/QIqwVXU09R5kSHaQ34Ru0VDaBNdDp9\n" +
                "        fhgTED91j66FhF+DC0DNggkEwY9fKaG3VndY5FihHnv2rgn9FnjCoVMEEVgQMD0Z\n" +
                "        JK1EFBHEU/ICfSVDJhpZozOCZ0ezq/0HCyum5CcVWDSuOuqVLmDJXEQ0gLKPCoB/\n" +
                "        pFk0eUmabHI6HXDRcIBfcKSnYFIOrE7H1gZsgLiYIONVhQZHqetUcnMG2Ogrb+TP\n" +
                "        n/9FfsYCkg1pxjk1t5HIKFAX1GUy+ugQicWScKI1WoFctzEw5Tsl8wyCsmbJTc8q\n" +
                "        arlTs6ym4/LiUg3u7Mzjc1v4adZ3e332iS22gYVGQUQo09i3wde+9oI1jVA7dQjL\n" +
                "        uPBptsN55IJ2KqgflpASL4KJWkOEM8eSKM84bsub8nJGHUKdowpNybxkS89nHjgd\n" +
                "        AuTDZkjCfLsjrOcCAwEAAaOCAaYwggGiMA8GA1UdEwEB/wQFMAMBAf8wDgYDVR0P\n" +
                "        AQH/BAQDAgEGMC0GA1UdEQQmMCSBInRlY2hub2xvZ3lnb3Zlcm5hbmNlQGJ3aW5w\n" +
                "        YXJ0eS5jb20wEQYJYIZIAYb4QgEBBAQDAgEGMB0GA1UdDgQWBBT/1jyNRxxnEm4r\n" +
                "        k8v6naiyXfBmRDCB7gYDVR0jBIHmMIHjgBSPn8UAHRvOguowBhgdrAwSyx1zNqGB\n" +
                "        v6SBvDCBuTELMAkGA1UEBhMCR0kxEjAQBgNVBAgTCUdpYnJhbHRhcjESMBAGA1UE\n" +
                "        BxMJR2licmFsdGFyMR0wGwYDVQQKExRQYXJ0eUdhbWluZyBTZXJ2aWNlczEdMBsG\n" +
                "        A1UECxMUSW5mb3JtYXRpb24gU2VjdXJpdHkxHDAaBgNVBAMTE1BhcnR5R2FtaW5n\n" +
                "        IFJvb3QgQ0ExJjAkBgkqhkiG9w0BCQEWF2luZm9zZWNAcGFydHlnYW1pbmcuY29t\n" +
                "        ggkA7fYMZ68EfkMwLQYDVR0SBCYwJIEidGVjaG5vbG9neWdvdmVybmFuY2VAYndp\n" +
                "        bnBhcnR5LmNvbTANBgkqhkiG9w0BAQsFAAOCAgEAWvJ0F0U3mc4AMwRajIo8KgWp\n" +
                "        Xgo8TfN/ZTAWu6KzCxQf4OJ4hKp2s5iGouv/r55AlJn21jDKASg3yIT8c4ttp9mz\n" +
                "        alMOMdniGAzyfoj1LdKOENcoCNx+m5FllAETvIWV8/FJ1SAqKdxmbxS3nct6d+8x\n" +
                "        mfMtlPDQqrVAnNfzhF2Kc5esJXcWe7JFqtQWe4lOQSs6PK8BaqBond7ohpeEhq8v\n" +
                "        87WyqYWHK46BBvs96bnBOrXW5k/jLOF2QT3a9y7taAN4+nHXZnWuMyQYE9brP0iB\n" +
                "        +eRG0NPJ5Mc95KwuTVHPP2jYgR+Ndwqwdv+gFCl6r8kREcfUx/a+5DGZzA/E8q1u\n" +
                "        vq501CKw70AVSYWPwB5ukUiJyWmBclKXXh9spZmLA4k3fqky8gi6ORK9BXw2LNeg\n" +
                "        Ohi12svjZWX3V5JPP1Xr+xCjLDUrmkUYu3Hyjczfrn+2uqCNgJKCZwpUJw9Bg8cE\n" +
                "        PNvtvAJnye/jFSOvoMpEwnPmhCrLWKPbIIjtzUq4qEq3WDky5me6izUX8Moq0Qjy\n" +
                "        PoWiOiVpajgMFtqLEJmL/80ZtezpyapNsxjAnfcGdNEz2TOEfXL4MdW8XalgYYop\n" +
                "        0xRr2zZJLrDBkA33/wRFKwYWydJfxUtwjXjJVH2cL78fbwRqdXUBbtya6eHqQvt6\n" +
                "        nd/rs17PPAaZ3mTyVMU=\n" +
                "        -----END CERTIFICATE-----" +
                "";


        static String cert2 = "-----BEGIN CERTIFICATE-----\n" +
                "MIIHaTCCBVGgAwIBAgIJAO32DGevBH5DMA0GCSqGSIb3DQEBCwUAMIG5MQswCQYD\n" +
                "VQQGEwJHSTESMBAGA1UECBMJR2licmFsdGFyMRIwEAYDVQQHEwlHaWJyYWx0YXIx\n" +
                "HTAbBgNVBAoTFFBhcnR5R2FtaW5nIFNlcnZpY2VzMR0wGwYDVQQLExRJbmZvcm1h\n" +
                "dGlvbiBTZWN1cml0eTEcMBoGA1UEAxMTUGFydHlHYW1pbmcgUm9vdCBDQTEmMCQG\n" +
                "CSqGSIb3DQEJARYXaW5mb3NlY0BwYXJ0eWdhbWluZy5jb20wIhgPMjAwNzA4MjEy\n" +
                "MjI4MTRaGA8yMDI3MDgxODIyMjgxNFowgbkxCzAJBgNVBAYTAkdJMRIwEAYDVQQI\n" +
                "EwlHaWJyYWx0YXIxEjAQBgNVBAcTCUdpYnJhbHRhcjEdMBsGA1UEChMUUGFydHlH\n" +
                "YW1pbmcgU2VydmljZXMxHTAbBgNVBAsTFEluZm9ybWF0aW9uIFNlY3VyaXR5MRww\n" +
                "GgYDVQQDExNQYXJ0eUdhbWluZyBSb290IENBMSYwJAYJKoZIhvcNAQkBFhdpbmZv\n" +
                "c2VjQHBhcnR5Z2FtaW5nLmNvbTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoC\n" +
                "ggIBAJ6+5xyetCr1iw2EVzMvcHqG2OGoeJqSl2IMDWlV4orjDIHZ5/AsyZgnOPAt\n" +
                "vgLn3loluAgkYuLxw0Hs30EO9jrOlE7jpMyIeDqjhlADWB1TvXMxpjBPhaVis+Yq\n" +
                "EZkcIvU5nAiznyHLPg9nCq/v2JIYrPim6sGkHF5KLVU6nyli/Teyoc1jUTidAqbr\n" +
                "zQ7Lt9LWjTZAfSHSFmt1NenWq4a5gHRAaODzPQp+QK39PKeAMuYELebQIIpMJfN5\n" +
                "pubk/zK/0fHJyYJB7H+24KTZ148UihhftaYM5FvId2PwWn+wIKuRcIu0uJZixzTj\n" +
                "whEfZULilk1MaT4Fv/k6wdix3uN/XD831ZTSz3fZeBzli9wPH9D2t0I+4xX6qA/G\n" +
                "r/ndlDlHjEaUAgdwjZr3UOEVQdVkcd2Rs/1+0knik2kvpS9jdXAaOputCKSjCHfT\n" +
                "DcxgUfDGtvOTSLnJ/IJnq4PmPbcs6Coa6kHvSno9BlyU+bgFfz9FWATHay5TC9ag\n" +
                "oclUQ1KIFlARFHNocZMY6w5Q7nMa1wS5OjWQTEb/gm1iYRMdXH2Yh/1jHwNcWtcx\n" +
                "ca6knB9KgHHkV//CEbdSzxNAVFQvTq1ir2Di9uczk3u8qG2IMd+DGlpgON5vfAbN\n" +
                "TznMojb+vFKQtSt1feXOrGiTZ0FoCwAaXw+k/ri7qsMN1y1nAgMBAAGjggFsMIIB\n" +
                "aDAPBgNVHRMBAf8EBTADAQH/MB0GA1UdDgQWBBSPn8UAHRvOguowBhgdrAwSyx1z\n" +
                "NjCB7gYDVR0jBIHmMIHjgBSPn8UAHRvOguowBhgdrAwSyx1zNqGBv6SBvDCBuTEL\n" +
                "MAkGA1UEBhMCR0kxEjAQBgNVBAgTCUdpYnJhbHRhcjESMBAGA1UEBxMJR2licmFs\n" +
                "dGFyMR0wGwYDVQQKExRQYXJ0eUdhbWluZyBTZXJ2aWNlczEdMBsGA1UECxMUSW5m\n" +
                "b3JtYXRpb24gU2VjdXJpdHkxHDAaBgNVBAMTE1BhcnR5R2FtaW5nIFJvb3QgQ0Ex\n" +
                "JjAkBgkqhkiG9w0BCQEWF2luZm9zZWNAcGFydHlnYW1pbmcuY29tggkA7fYMZ68E\n" +
                "fkMwEQYJYIZIAYb4QgEBBAQDAgEGMCIGA1UdEQQbMBmBF2luZm9zZWNAcGFydHln\n" +
                "YW1pbmcuY29tMA4GA1UdDwEB/wQEAwIBBjANBgkqhkiG9w0BAQsFAAOCAgEAKxvk\n" +
                "sX8fi8ZhR/2wTYnUIWO+GsVIisXgd4h4yaZhVo6BB4ab3rrZZ8Zk0gKSfGRsOfUh\n" +
                "2xa6O0xY/cUngHmElvwymf+dwzKF6dQovdl5DtIn6NVB6TSn4vYc9BIMzMZkbo+m\n" +
                "Pyz4HRP6mCUTYbuv7r/0OY95KAOhKYA94/UXsA1owtIGmxYbMeJQQxftz5qq7TiQ\n" +
                "e8hdQQts8+CXBIOLYFUeNJv18YVyKWUnuSn90IBskQY6IlYasAeuI4/V0QE3aHd7\n" +
                "3W/YSDxEo0KtdJKnU4vB3dC1GarwkcxT+irJgf3TYCFAYXyC0AxEZi40qCCKmfG6\n" +
                "zcRQtCKVJclRbfQNXVSczGNH6ntdbn6G31Ku7JFoskIs+euWr3DcEEZ3HI0cXSTn\n" +
                "/YE+WW85xYyD0fa70md/XeldTkSh0UO1VHmEBfWkgNSJb9HUliXKEUlJFA3fhFM1\n" +
                "Q4NfpKSsBv4cQYlgTQaBSurBulDLCizAQ/hbElLdJh5RSCXVgdQmhl0Ycaohj+37\n" +
                "JzK9Sk6oBSUqhrvrjYAkyGUZmIlmYA9IHmtbQ87oTsGmk3tGslrXXwzKkvfHxV5+\n" +
                "FkxQw4eBJPpeayySMkSj4BIZdaOe25fOLZbMaw9e8NSZcmYTsJdsXJeFjj1Uypwv\n" +
                "50f29VVoTFYBOAT+QHb3X5aSN9XakuClgEl2pi8=\n" +
                "-----END CERTIFICATE-----";

        static String cert3 = "-----BEGIN CERTIFICATE-----\n" +
                "MIIFbjCCA1agAwIBAgIIBvC6OlRNcE8wDQYJKoZIhvcNAQELBQAwOzELMAkGA1UE\n" +
                "BhMCR0kxEjAQBgNVBAcTCUdpYnJhbHRhcjEYMBYGA1UEAxMPUk1HIFJTQSBSb290\n" +
                "IENBMB4XDTI0MDIyNjE0MDAwMFoXDTM5MDIyNjE0MDAwMFowOzELMAkGA1UEBhMC\n" +
                "R0kxEjAQBgNVBAcTCUdpYnJhbHRhcjEYMBYGA1UEAxMPUk1HIFJTQSBUZXN0IENB\n" +
                "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAqjrxeXYCGAPFRbogGFfe\n" +
                "BqrdmNGOh84W98lbEcfu9UHZgf84+GbMQx5XPQwq8dlk/uN43gPvniTIpyEVObJx\n" +
                "1NNJEmDd1+hZmJ1zFYmkaD+frN5I6XClm8Pkb1YHxvQ9pJEnsq75gNxeHbw8OFuC\n" +
                "a5EPMpU6GpV9rOZMe90W3RCL7A+WDApt+139hO/26sMUtZRzUZYoNZHspB6/zqjE\n" +
                "YOqWNL0jn9mdt4yb5Zr8B/uSq5Ws0LdmPPUvExsUSJnR4u4UrdiBcf6dPQJpam+K\n" +
                "93XrygnQf1JujooayzyGbSPZtElZT79m/ZyJen/32u7hWIZTka3hOPOjcYeHiGyQ\n" +
                "VJJd3s/GyKEpC/JBUfU015nMj36Od6NLbJgSg+7lpbmWxggO//yIIZwEKGY8LlWX\n" +
                "9ZLiHGFPYBUKXu12W27iFLpSEY1fcxtB1xStejSAMNIRdZxkzk4mvg+tPWcZ6sVJ\n" +
                "jKcv4jpTCEEB+9G5xW8sxn+0c0lbI7ERrh3NUeQzu1s0Ajk0eD0uOJFhAAEescSq\n" +
                "+Na0edJMoLW1J6ZxmFoHDoUZK+Q1Wt9aKpgjpP9tMg5iyizaSEwKTqmvJ8v8gZe+\n" +
                "9PrGg2EUP9/MgdCmpeCXti+aArIAhVP5Ym2i7B9GWmUQQJxAJrUNNsJt2356jkj7\n" +
                "mGk23DCfkd2/GH6Mmsn1qQ8CAwEAAaN2MHQwDwYDVR0TAQH/BAUwAwEB/zAdBgNV\n" +
                "HQ4EFgQUQoOw8S1rdgXLMG0erR4GaxP5onEwHwYDVR0jBBgwFoAUmzYlhNA/KnV4\n" +
                "3nPn/dwY9xOZpxAwDgYDVR0PAQH/BAQDAgEGMBEGCWCGSAGG+EIBAQQEAwIBBjAN\n" +
                "BgkqhkiG9w0BAQsFAAOCAgEASy4+x6TaXySPCeskxcB+79nfV5gVgTKxXIW7HxRC\n" +
                "3enlAkgCcjP1QfC/RInjEb/msLOxT4YuRoLIvjB6KgubZDjc9FZDXM5NPRNJ8HmS\n" +
                "TZqhAnNbWfQlKwk0D8qCKzjNPRWehAG72cuLnfO5l5zyWJrIhyDBcV/eS0J6R20I\n" +
                "IdawDRTUQ52xBaezZFI6/IR8yDdhzujB9X6BGwZwAam0QshXAm6L1LOxfrET5fvq\n" +
                "fC0F4zUlezRrgN96JTIl4AeHojcj4hgSXnM03DzsQbsoRvS99IzX/woxB69E+YG4\n" +
                "M96W70SyQcJy7rbHHjlfjCrtXusAbNRmBZtScRY/j5EXIvbcxwI2Ngc6om4y/Wd+\n" +
                "5c2lfC4Ix3HDDxVQ08tmttynQ34lpkn3eDMbfmiHXoIK+2qx4JTHsChsMACbZqlZ\n" +
                "45Xg1wH+QqB6ex2Qhs8U72XyxDnshpqkB+z+cWQ2zdo6MRZmm7wk1y1q4Hu5ni3t\n" +
                "ad9GwqZv9XvO64EajCjerKqv36RjFYi4484oRrvy6eitXPfvfaKHji4wqf0EI+0X\n" +
                "VkRBQ9fl//8MmCjJnVkVN8gGNa7Bymowd0XkAFNHWiav46tVqEupYhL53NPdBd2/\n" +
                "uZc6OQM8KMuEXRS3FJb4UnZYiePJPJa5N7dahv7NKbwLX4M7A8y47zUp+YVQ7ZyD\n" +
                "s5U=\n" +
                "-----END CERTIFICATE-----";
        static String cert4 = "-----BEGIN CERTIFICATE-----\n" +
                "MIIFbjCCA1agAwIBAgIIJGwS/KeMmLcwDQYJKoZIhvcNAQELBQAwOzELMAkGA1UE\n" +
                "BhMCR0kxEjAQBgNVBAcTCUdpYnJhbHRhcjEYMBYGA1UEAxMPUk1HIFJTQSBSb290\n" +
                "IENBMB4XDTI0MDIxNTE1NDAwMFoXDTM5MDcxNTE1NDAwMFowOzELMAkGA1UEBhMC\n" +
                "R0kxEjAQBgNVBAcTCUdpYnJhbHRhcjEYMBYGA1UEAxMPUk1HIFJTQSBSb290IENB\n" +
                "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAyTP94OvuYQOqNlL6aYou\n" +
                "KSvoA4Uj7ZJXfsiRXAObrK+tIRzJSRVkzYzQZ/zeKwV1W8N0Xq89LffIiIG4rGrd\n" +
                "BGjPfZBfC7+f/IJOw9cVbBnaHrs3memraOHz41xJPAWlzrv9Ub9An4VT8//2AYCF\n" +
                "Mj2dtXsSVE9KsnNF1/ZMez+9m1SRlSrvmV4RDdQfXzNggv69mqLI+ym0oWJBhjGx\n" +
                "2cDkRUdLuzg5fDRJepmb7UZIdvoV2eplaxM94gUsd+AOYsBqpwaEc32DQ3tWhR66\n" +
                "ABmPIGychUJaK7mD/6MefnusQzCpRmO12F/3wxlYrTAQUc1F1kF1lXOTehX66KlG\n" +
                "gc324AUXLX2D1BwYr/GK1cv9Frh0Y7p9DzvwWqQbIlDc84/vjoRbaagARR6cGkI6\n" +
                "csR/8UaPASA4ki8AlKFnwWUdjKxU5iMPOUoime5FSFzHBwVuWLTWGzUhrH95TDxA\n" +
                "d1dID+R2H9c3X500qOStGwubBusWoOKpsm4oNUHytJ2ZHUZROcz4b7byOpiPDuUR\n" +
                "AkvDgkUYAgWIQs8FEcwOswTTF/sUYziLO6Z91LEK+2+zXST3y17oH7XyZ7qTQrTj\n" +
                "fpnjstj8YMo7+I4WZmh9DjmLbisENgfJsO3iT+P7WR0CiKdj9I78ollfq38FrrI6\n" +
                "0kO91n6hgC7TYpWGWccVCpMCAwEAAaN2MHQwDwYDVR0TAQH/BAUwAwEB/zAdBgNV\n" +
                "HQ4EFgQUmzYlhNA/KnV43nPn/dwY9xOZpxAwHwYDVR0jBBgwFoAUmzYlhNA/KnV4\n" +
                "3nPn/dwY9xOZpxAwDgYDVR0PAQH/BAQDAgEGMBEGCWCGSAGG+EIBAQQEAwIBBjAN\n" +
                "BgkqhkiG9w0BAQsFAAOCAgEANgqB43FpfwuJA+G1iVuCBXG+RXCirrCqrF0PSW2U\n" +
                "ivcRb6u5fK/TPXxb2CYvX2ZYpHDgB4sgb/Dr1yEiIJgKjngOjo75LXO+WvKAMhmR\n" +
                "yHDNZk+gsgr3UyYa37VwAZmRXbUsEar757/APgdoScHyQ7RYlL/WOOIvIqSqJhWD\n" +
                "lSkIgU1UfFfj1oBdPRy8Xg5n4Hz6j2h/1uMz4tEDPMTU4CfVfbkJ8GeUC2LPT0J5\n" +
                "bHoKuvWOt5Wrlo30rcbNFZTDzXzpUi8qxRJvQmN8Bk5DJs6zE/LSyr2x4o+VwjRc\n" +
                "hi0IUPCGxq3k8TM1NPp9otbF3FQIrxVsm/8GUcpVcQ9XIivG/WvQkCHoetX87DUo\n" +
                "NNWT0duuSLfRwe1NbWDV6xoQ78GARfgFNLX5RDjDxn3j1z2c6vw9oO3BjSMbIRw8\n" +
                "hZ7Rstu173boGpJ2Ls4s/MjArVtHRorQF6yCPbDXvV+ryuWocrOkT+q5A3GQPjiw\n" +
                "xRPjgcZjPy22dbjzC30zuq+5QHph4WcyyVxyHiMg/XXClgq7m9iFwzC0TZYW8+wQ\n" +
                "O1Uk7zF7SFkD8y8tx9PKfwx+PFo04LeXeTPBYobLYom3dr7Yr7OFPz3VE59OvQRH\n" +
                "DNlNzQz8OVPTENiPqqiz/WMyIIb9MxrU/+uqmFy7mMC415GzM3JqUe6eB6CFI9CH\n" +
                "KeE=\n" +
                "-----END CERTIFICATE-----";
    }


    public class RMGRSAProdCertificates {
        String cert1 = "";
        String cert2 = "";
        String cert3 = "";
        String cert4 = "";
    }

    public char[] getHexDecimalCharCert(String cert) {
        byte[] certBytes = cert.getBytes();
        if (certBytes != null) {
            StringBuilder hexStringBuilder = new StringBuilder(certBytes.length * 2);
            for (byte b : certBytes) {
                hexStringBuilder.append(String.format("%02X", b));
            }
            // Convert the hexadecimal string to a char array
            return hexStringBuilder.toString().toCharArray();
        } else {
            return null;
        }
    }

    public X509Certificate[] getCertificates() {

        String  charCert1 = getChars(RMGRSACertificates.RMGRSANonProdCertificates.cert1);
        String charCert2 = getChars(RMGRSACertificates.RMGRSANonProdCertificates.cert2);
        String charCert3 = getChars(RMGRSACertificates.RMGRSANonProdCertificates.cert3);
        String charCert4 = getChars(RMGRSACertificates.RMGRSANonProdCertificates.cert4);



        System.out.println(charCert1);
        System.out.println();
        System.out.println(charCert2);
        System.out.println();
        System.out.println(charCert3);
        System.out.println();
        System.out.println(charCert4);
        System.out.println();

        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            return new X509Certificate[]{
                    /*(X509Certificate) certificateFactory.generateCertificate(
                            new ByteArrayInputStream(RMGRSANonProdCertificates.cert1.getBytes())
                    ),

                    (X509Certificate) certificateFactory.generateCertificate(
                            new ByteArrayInputStream(RMGRSANonProdCertificates.cert2.getBytes())
                    ),
                    (X509Certificate) certificateFactory.generateCertificate(
                            new ByteArrayInputStream(RMGRSANonProdCertificates.cert3.getBytes())
                    ),
                    (X509Certificate) certificateFactory.generateCertificate(
                            new ByteArrayInputStream(RMGRSANonProdCertificates.cert4.getBytes())
                    ),*/


                    /*(X509Certificate) certificateFactory.generateCertificate(
                            new ByteArrayInputStream(getBytes(charCert1))
                    ),
                    (X509Certificate) certificateFactory.generateCertificate(
                            new ByteArrayInputStream(getBytes(charCert2))
                    ),
                    (X509Certificate) certificateFactory.generateCertificate(
                            new ByteArrayInputStream(getBytes(charCert3))
                    ),
                    (X509Certificate) certificateFactory.generateCertificate(
                            new ByteArrayInputStream(getBytes(charCert4))
                    ),*/
            };
        } catch (Exception e) {
            return new X509Certificate[]{};
        }
    }

    public static String getChars(String cert) {
        byte[] inputBytes = cert.getBytes();
        int length = inputBytes.length;
        //String[] chars = new String[length];
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (byte inpByte : inputBytes) {
            char ch = (char) (inpByte & 0xFF);
            if(ch != '-') {
                str.append("0x").append(ch).append(",");
            }
            i++;
        }
        return str.toString();
    }

    public static byte[] getBytes(char[] inputChars) {

        byte[] bytes = new byte[inputChars.length];
        int i = 0;
        for (char inpChar : inputChars) {
            bytes[i++] = (byte) (inpChar & 0xFF);
        }
        return bytes;
    }

}

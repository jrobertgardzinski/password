# TODO

## Argon2Config

- [ ] `Argon2Config` powinno implementować `PropertiesConfigSource`
  - aktualnie ma tylko statyczną metodę `from(PropertiesConfigPort<String>)`

- [ ] Rozróżnienie `PropertiesConfigSource` vs `PropertiesConfigPort`:
  - `PropertiesConfigPort` — interfejs (functional), implementowany przez adapter w warstwie infra (np. Spring, Micronaut)
  - `PropertiesConfigSource` — klasa domenowa, trzyma `PropertiesConfigPort` jako field

- [ ] Test `loadsFromProperties` w `Argon2ConfigRulesTest` do przepisania
  - powinien weryfikować że `Argon2Config` implementuje `PropertiesConfigSource`, nie testować konkretnych wartości

## Testy VO

- [ ] Refaktoryzacja `IterationsRulesTest`, `MemLimitInKBRulesTest`, `ParallelismRulesTest`
  do abstrakcyjnej klasy bazowej `BoundedIntRulesTest`
  - subklasy implementują `min()`, `max()`, `create(int)`
  - eliminuje powielony kod testów `acceptsValidValues` / `rejectsInvalidValues`

# TODO

Tylko otwarte rzeczy. Historia zrobionego = git log.

## Otwarte — use-case'y (2026-09-02)

- **GenerateCompliantPassword** (Robert: „dać możliwość wygenerowania hasła i chuj"). Ląduje w
  `password-usecase` obok `CreatePasswordHash` — moduł ma wtedy DWA use-case'y.
  - Reguła: wygenerowane hasło spełnia KAŻDY constraint `PasswordPolicy` w mocy (długość,
    znaki specjalne z zestawu polityki, wielka, mała, cyfra). Test naturalny jako property jqwik:
    dla każdego wygenerowanego hasła `policy.constraints()` przechodzi w komplecie.
  - Wariacje: krótkie / średnie / długie (długości względem minimum polityki, np. min, min+4,
    min+12 — do ustalenia). „Można poszaleć": wymawialne, bez znaków mylących (l/1/O/0) itd.
  - Klienci: konta zakładane przez admina, hasła tymczasowe, glue testowe („a valid password"
    względem polityki — patrz microservice-security/specs/README.md o próbkach stopnia Rebuild).
  - Losowość: `SecureRandom` w use-casie; port tylko jeśli testy potrzebują determinizmu.
  - Drabinka (`password-ladder`) dla tego use-case'u: MOŻLIWA (np. żywe długości wariantów),
    ale „nie ma musu" — najpierw sam use-case.
- Kandydaci omówieni, NIE zaplanowani: VerifyAndUpgradeHash (rehash po zmianie parametrów
  argon2 — domyka wzorzec drabinki po stronie hashowania; dziś serwis woła `verify` wprost w
  3 miejscach), NotReused (port historii hashów), NotBreached (k-anonimowość), MaxAge (wymaga
  znacznika czasu w `HashedPassword`). Odrzucony: estymator siły obok polityki.

## Otwarte — dowody (2026-09-02)

- `password-ladder` ma ZERO testów; zachowanie sprawdzone tylko przez HTTP w microservice-security
  (password-policy.feature). Do dopisania w konwencji biblioteki (jqwik + etykiety Allure):
  `SetMinPasswordLengthRulesTest` (≥5 przyjęte i zapisane, <5 odrzucone z powodem i bez zapisu),
  `LadderedPasswordPolicyRulesTest` (długość z drabinki, 4 pozostałe DEFAULT; wiersz 3 spada do
  domyślnej), `MinLengthLadderRulesTest` (klucz, bramka).
- ADR 0008 w shared/docs/adr: moduł `*-ladder` jest osobny z JEDNEGO powodu — wnosi port
  persystencji (`MinLengthRepository`) i zależność od `config` (ladder), których rdzeń
  domain/config/usecase nie chce mieć. `*-ladder` zależy od rdzenia, nigdy odwrotnie; serwis
  wnosi tylko adaptery portów, okablowanie i cache TTL; `*-ladder` niesie własny dowód.
  NIE jest to reguła „każda biblioteka dostaje ladder": drugi moduł ladder powstaje dopiero,
  gdy jakaś reguła faktycznie ma być zmieniana na żywo. Kandydat: `email-ladder` (listy domen).
  `argon2` NIE potrzebuje drabinki (parametry hashowania zmienia się przez rehash, nie na żywo).

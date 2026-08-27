INSERT INTO node (
    codename,
    district,
    alias,
    operator_name,
    status,
    last_seen,
    decommissioned_at
)
VALUES
    (
        'NYX-7',
        'Kallio',
        'Ghost Relay',
        NULL,
        'ACTIVE',
        TIMESTAMP WITH TIME ZONE '2026-08-27 18:42:00+03:00',
        NULL
    ),
    (
        'VANTA-3',
        'Kamppi',
        NULL,
        'Helix Transit',
        'DEGRADED',
        TIMESTAMP WITH TIME ZONE '2026-08-27 17:58:00+03:00',
        NULL
    ),
    (
        'EMBER-12',
        'Pasila',
        'Red Echo',
        'Satori Grid',
        'OFFLINE',
        TIMESTAMP WITH TIME ZONE '2026-08-18 03:21:00+03:00',
        TIMESTAMP WITH TIME ZONE '2026-08-20 12:00:00+03:00'
    );

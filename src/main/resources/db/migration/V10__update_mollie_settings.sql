UPDATE setting SET read_permission = 'admin:payment', write_permission = 'admin:payment';

UPDATE setting SET write_only = true WHERE key = 'MOLLIE_API_KEY';
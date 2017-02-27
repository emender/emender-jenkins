select product_names.name, product_versions.version, guides.name, test_suites.name, tests.name, waives.cause
    from waives
         join tests            on waives.test_id = tests.id
         join test_suites      on tests.test_suite_id = test_suites.id
         join guides           on waives.guide_id = guides.id
         join product_versions on  guides.prod_ver_id = product_versions.id
         join product_names    on product_versions.product_id = product_names.id
    where product_names.name = 'Red Hat Enterprise Linux'
      and product_versions.version = '6.9'
      and guides.name = 'Installation Guide 3'
      and test_suites.name = 'Test suite 1'
      and tests.name       = 'Test #1 for suite #1'
         ;


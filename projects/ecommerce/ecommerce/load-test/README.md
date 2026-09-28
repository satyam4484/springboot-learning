artillery run \
  --output reports/products-report.json \
  products-load.yml

artillery report \
  --output reports/products-report.html \
  reports/products-report.json

open reports/products-report.html



artillery run products-load.yml  --record --key a9_r6kyigt3mliyu1tjve83wv7dro2e2grs
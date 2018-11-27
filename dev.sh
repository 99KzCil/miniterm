cd vue
npm run dev &
export NPM_PID=$!
cd ..
mvn spring-boot:run
kill $NPM_PID

# myblog
myblog backend server

# Use Case

![Mind Map](https://user-images.githubusercontent.com/104721095/180597197-bfdbe91a-b5c3-4365-b2b5-43594bae9b1f.jpg)

# API 명세서

![명세서](https://user-images.githubusercontent.com/104721095/180922675-16d605db-58ae-431b-a56d-45745e9e3593.png)

1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)  

    게시글의 수정은 http의 PUT요청을 하고 게시글의 id와 password를 param으로 받아서 password가 db의 password와 일치하였을 경우 
    DTO의 생성자를 이용하여 update하고 jpa의 save메소드를 이용하여 저장하였습니다.
    게시글의 삭제는 http의 DELETE요청을 하고 게시글의 id와 password를 param으로 받아서 password가 db의 password와 일치하였을 경우
    jpa의 deleteById 메소드를 이용하여 삭제하였습니다.
    (2022-07-26) 추가  
    요구사항에 비밀번호를 입력받지않고 수정 삭제 가능하게 하게 하라고 바뀌어서 수정하였습니다.
  
2. 어떤 상황에 어떤 방식의 request를 써야하나요?


    조회할때는 GET, 생성할때는 POST, 수정때는 PUT, 삭제할때는 DELETE request를 써야합니다.    
  
3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?


    각각 요청에 맞는 request를 하였습니다. 다만 비밀번호를 CHECK할때 GET요청을 사용하였는데 이 부분이 RESTFUL하지
    못하다고 생각합니다 PW를 CHECK할때는 어떤 request를 해야할지 좀 더 고민해봐야겠습니다.\
    (2022-07-26) 추가  
    예제의 명세서와 같이 GET 메소드가 아닌 POST 메소드로 요청을 @RequestBody를 통해 비밀번호를 받아서 체크하게 수정하였습니다.
  
4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)  


    View가 없는 API만을 만들었기때문에 RestController를 이용하였고 Repository는 JpaRepository를 상속 받아서 이용하였고 Service에는 update와 checkPassword
    그리고 password를 복호화하여 저장해주는 save 메소드를 만들어서 이용하였습니다.   
  
5. 작성한 코드에서 빈(Bean)을 모두 찾아보세요!

    의존성 주입을 한 PostRepository와 PostService가 있을 것 같습니다.  
  
  
6. API 명세서 작성 가이드라인을 검색하여 직접 작성한 명세서와 비교해보세요!  

    가이드라인과 response가 최대한 비슷하게 하기위해서 apiResponse라는 package를 만들어서 요청에따라 success와 error값을 전달하도록 하였고, response 값에 특정
    컬럼을 제외하기 위해 PostDetailMapping과 PostMapping이라는 interface를 만들어서 리턴할 수 있도록 하였습니다.

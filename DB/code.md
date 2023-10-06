Table users {
  id integer [primary key]
  username varchar
  friends text
}

Table Post {
  id integer [primary key]
  title varchar
  body text
  music_id integer
  user_id integer
  created_at timestamp
  like integer
}

Table Comment {
  id integer [primary key]
  post_id integer
  user_id integer
  content text
  created_at timestamp
}

Table Music {
  id integer [primary key]
  post_id integer
  link varchar
  title varchar
}

Ref: Post.user_id > users.id
Ref: Comment.user_id > users.id
Ref: Comment.post_id > Post.id
Ref: Music.post_id > Post.id
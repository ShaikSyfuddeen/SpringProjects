package com.springboot.blogapp.service;

import com.springboot.blogapp.entity.Category;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.payload.PostDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.springboot.blogapp.payload.PostResponse;
import com.springboot.blogapp.repository.CategoryRepository;
import com.springboot.blogapp.repository.PostRepository;
import com.springboot.blogapp.utils.AppConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private PostRepository postRepository;

    @Test
    public void createPostTest_returnsDTO() {
        PostDTO postDTO = new PostDTO();
        long id = 1L;
        postDTO.setTitle("Test Post");
        postDTO.setDescription("Test Post For Testing");
        postDTO.setContent("Post content");
        postDTO.setCategoryId(id);

        Category category = new Category(id, "Test Category", "Test Category");
        Post post = modelMapper.map(postDTO, Post.class);
        post.setCategory(category);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(postRepository.save(Mockito.any())).thenReturn(post);

        assertEquals(PostDTO.class, postService.createPost(postDTO).getClass());
    }

    @Test
    public void createPostTest_returnsResourceNotfoundException() {
        PostDTO postDTO = new PostDTO();
        long id = 1L;
        postDTO.setTitle("Test Post");
        postDTO.setDescription("Test Post For Testing");
        postDTO.setContent("Post content");
        postDTO.setCategoryId(id);

        Category category = new Category(id, "Test Category", "Test Category");
        when(categoryRepository.findById(id)).thenThrow(new ResourceNotFoundException("Category", "id", postDTO.getCategoryId()));
        assertThrows(ResourceNotFoundException.class, () -> postService.createPost(postDTO).getClass());
    }

    @Test
    public void createPostTest_returnsNullPointerException() {
        PostDTO postDTO = new PostDTO();
        long id = 1L;
        postDTO.setTitle("Test Post");
        postDTO.setDescription("Test Post For Testing");
        postDTO.setContent("Post content");
        postDTO.setCategoryId(id);

        Category category = new Category(id, "Test Category", "Test Category");
        Post post = modelMapper.map(postDTO, Post.class);
        post.setCategory(category);
        when(categoryRepository.findById(id)).thenReturn(null);
        assertThrows(NullPointerException.class, () ->postService.createPost(postDTO).getClass());
    }

    @Test
    public void getAllPostsTest_Success() {
        Page<Post> posts = getpostPage();

        Sort sort = Sort.by("id").descending();
        // create Pageable instance
        Pageable pageable = PageRequest.of(0, 10, sort);
        when(postRepository.findAll(any(Pageable.class))).thenReturn(posts);
        assertEquals(PostResponse.class, postService.getAllPosts(0, 10, AppConstants.DEFAULT_SORT_BY, "desc").getClass());
    }

    @Test
    public void getAllPostsTest_returnsNullPointerException() {
        Page<Post> posts = null;
        when(postRepository.findAll(any(Pageable.class))).thenReturn(posts);
        assertThrows(NullPointerException.class, () -> postService.getAllPosts(0, 10, AppConstants.DEFAULT_SORT_BY, AppConstants.DEFAULT_SORT_DIR).getClass());
    }

    @Test
    public void getPostByIdTest_returnsDTO() {
        long id = 1L;
        Post post = new Post(id, "Test Post", "Test post description", "Test post content");
        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        assertEquals(PostDTO.class, postService.getPostById(id).getClass());
    }

    @Test
    public void getPostByIdTest_returnsResourceNotfoundException() {
        long id = 1L;
        when(postRepository.findById(id)).thenThrow(new ResourceNotFoundException("Post", "id", id));
        assertThrows(ResourceNotFoundException.class, () -> postService.getPostById(id));
    }

    @Test
    public void getPostByIdTest_returnsNullPointerException() {
        long id = 1L;
        Post post = new Post(id, "Test Post", "Test post description", "Test post content");
        when(postRepository.findById(id)).thenReturn(null);
        assertThrows(NullPointerException.class, ()-> postService.getPostById(id).getClass());
    }

    @Test
    public void updateDTOTest() {
        long id = 1L;
        Post post = new Post(id, "Test Post", "Test post description", "Test post content");
        Category category = new Category(id, "Test Category", "Test Category");

        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        when((categoryRepository.findById(id))).thenReturn(Optional.of(category));


        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Updated Test Post");
        postDTO.setDescription("Updated Test Post For Testing");
        postDTO.setContent("Updated Post content");
        postDTO.setCategoryId(id);

        Post updatedPost = modelMapper.map(postDTO, Post.class);
        when(postRepository.save(Mockito.any())).thenReturn(updatedPost);

        assertEquals("Updated Test Post", postService.upadteDTO(postDTO, id).getTitle());
        assertEquals("Updated Test Post For Testing", postService.upadteDTO(postDTO, id).getDescription());
        assertEquals("Updated Post content", postService.upadteDTO(postDTO, id).getContent());
    }

    @Test
    public void deletePostById_Success() {
        long id = 1L;
        Post post = new Post(id, "Test Post", "Test post description", "Test post content");

        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        postService.deletePostById(id);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    public void deletePostById_Fail() {
        long id = 1L;
        when(postRepository.findById(id)).thenReturn(null);
        verify(postRepository, times(0)).delete(any(Post.class));
    }

    @Test
    public void getPostsByCategoryTest() {
        long id = 1L;
        Category category = new Category(id, "Test Category", "Test Category");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Post post1 = new Post(id, "Test Post-1", "Test post-1 description", "Test post-1 content");
        Post post2 = new Post(id, "Test Post-2", "Test post-2 description", "Test post-2 content");

        when(postRepository.findByCategoryId(id)).thenReturn(List.of(post1, post2));

        assertNotEquals(null, postService.getPostsByCategory(id));
    }

    private Page<Post> getpostPage() {
        return  new Page<Post>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Post, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Post> getContent() {
                return List.of();
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Post> iterator() {
                return null;
            }
        };
    }
}

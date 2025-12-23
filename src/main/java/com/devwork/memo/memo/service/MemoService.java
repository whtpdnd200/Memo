package com.devwork.memo.memo.service;

import com.devwork.memo.common.FileManager;
import com.devwork.memo.memo.domain.Memo;
import com.devwork.memo.memo.repository.MemoRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public boolean createMemo(Long id
                            , String title
                            , String contents
                            , MultipartFile imageFile) {

        String imagePath = FileManager.savaFile(id, imageFile);

        Memo memo = Memo.builder()
                        .userId(id)
                        .title(title)
                        .contents(contents)
                        .imagePath(imagePath)
                        .build();
        try {
            memoRepository.save(memo);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public List<Memo> getMemoList(long userId) {


        return memoRepository.findByUserId(userId, Sort.by("id").descending());
    }

    public Memo getMemo(long id) {

        Optional<Memo> optionalMemo = memoRepository.findById(id);

        return optionalMemo.get();

    }

    public boolean updateMemo(long id, String title, String contents) {

        Optional<Memo> optionalMemo = memoRepository.findById(id);

        if(optionalMemo.isPresent()) {
            Memo memo = optionalMemo.get();

            memo = memo.toBuilder()
                    .title(title)
                    .contents(contents)
                    .build();
            try {
                memoRepository.save(memo);
            } catch(DataAccessException e) {
                return false;
            }

        } else {
            return false;
        }

        return true;
    }

    public boolean deleteMemo(long id) {

        Optional<Memo> optionalMemo = memoRepository.findById(id);

        if(optionalMemo.isPresent()) {
            Memo memo = optionalMemo.get();

            FileManager.removeFile(memo.getImagePath());

            try {
                memoRepository.delete(memo);

            } catch(DataAccessException e) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }
}

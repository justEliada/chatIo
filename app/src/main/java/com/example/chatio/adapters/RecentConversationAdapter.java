package com.example.chatio.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatio.databinding.ItemContainerRecentConversationBinding;
import com.example.chatio.listeners.ConversationListener;
import com.example.chatio.models.ChatMessage;
import com.example.chatio.models.User;

import org.checkerframework.checker.units.qual.C;

import java.util.List;


public class RecentConversationAdapter extends RecyclerView.Adapter<RecentConversationAdapter.ConversationViewHolder> {
    private final List<ChatMessage> chatMessages;
    private final ConversationListener conversationListener;

    public RecentConversationAdapter(List<ChatMessage> chatMessages, ConversationListener conversationListener) {
        this.chatMessages= chatMessages;
        this.conversationListener = conversationListener;
    }


    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationViewHolder(
                ItemContainerRecentConversationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false)
        );

    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder {
        ItemContainerRecentConversationBinding binding;

        ConversationViewHolder(ItemContainerRecentConversationBinding itemContainerRecentConversationBinding) {
            super(itemContainerRecentConversationBinding.getRoot());
            binding = itemContainerRecentConversationBinding;
        }

        void setData(ChatMessage chatMessage){
            binding.imageProfile.setImageBitmap(getConversationImage(chatMessage.conversationImage));
            binding.textName.setText(chatMessage.conversationName);
            binding.textRecentMessage.setText(chatMessage.message);

            binding.getRoot().setOnClickListener(v -> {
                User user = new User();
                user.id = chatMessage.conversationId;
                user.username = chatMessage.conversationName;
                user.image = chatMessage.conversationImage;
                conversationListener.onConversationClicked(user);
            });


        }

        private Bitmap getConversationImage(String encodedImage){
            if(encodedImage == null || encodedImage.isEmpty()) {
                return null;
            }
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }




    }
}

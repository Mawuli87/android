package com.messieyawo.books;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder>{
    //we also need a constructor that takes an array list of books as an argument
    //lets also define an arraylist of books that we use throughout the class.
    ArrayList<Book> books;
    public BooksAdapter(ArrayList<Book> books){
//inside the constructor lets set our books to the books argument in the constructor
        this.books = books;

    }
    @NonNull
    @Override
    //oncreateviewholder is called when the recyclerView needs a view Holder
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //in the oncreate viewholder, we need to inflate the layout we have defined
        //now let get the context
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(itemView);//now lets to the onBindViewHolder to get the view at the current position
    }

    @Override
    //onBindViewHolder is called to display the data
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
// here we will get now we will get the view at the current position and then bind it to the holder
        Book book = books.get(position);
holder.bind(book);
    }

    @Override
    public int getItemCount() {
        //for the getitem count lets return the size of the book array list
        return books.size();
        //now to complete all we need to delete the taskview from MainActivity
        //and place a recyclerView and then we need to write the data into the recyclerView through our
        //adapter
    }
    //the adapter needs a few method to be implemented

    //in the adapter lets create a viewholder first
    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //as you can see android studio is complaining this is because we need to have a constructor here
        //now we need to declare our textView as member of the class
        TextView tvTitle;
        TextView tvAuthors;
        TextView tvDate;
        TextView tvPublisher;
        public BookViewHolder(@NonNull View itemView) {

            super(itemView);
            //in the constructor we will call findViewById for each textView
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthors = itemView.findViewById(R.id.tvAuthors);
            tvDate = itemView.findViewById(R.id.tvPublisheDate);
            tvPublisher = itemView.findViewById(R.id.tvPublisher);
            itemView.setOnClickListener(this);


        }

        //now we will create a method call bind that will take as an argument a book
        //in this method we will bind the book data to the textView we have defined
        public void bind (Book book){
//we will begin with the title
            tvTitle.setText(book.title);
            //authhors is an array we need to define first a string to contain them




            //textView define the view and binds the data
            //now lets go to the adapter and extends the recyclerView
            tvAuthors.setText(book.authors );
            tvDate.setText(book.publishedDate);
            tvPublisher.setText(book.publisher);


        }


        @Override
        public void onClick(View view) {
 int position = getAdapterPosition();
 Book selectedBook = books.get(position);
            Intent intent = new Intent(view.getContext(),BookDetail.class);
            //then lets make use of our parcelable class by putting extras
            intent.putExtra("Book",selectedBook);
            //and finaly lets call the start activity method
            view.getContext().startActivity(intent);

            //the in the bookdetail activity let' read the book from the bandle
        }
    }
}

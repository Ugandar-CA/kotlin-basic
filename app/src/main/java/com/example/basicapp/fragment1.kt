package com.example.basicapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class fragment1 : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private var apiInterface : ApiInterface? = null
    private var items = ArrayList<pojoConvert>()
    lateinit var fragAdapter : FragAdapter
    private val s:String = "First Fragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView =view.findViewById(R.id.recycle)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
        apiInterface!!.getData().enqueue(object  : Callback<ArrayList<pojoConvert>>{
            override fun onResponse(
                call: Call<ArrayList<pojoConvert>>,
                response: Response<ArrayList<pojoConvert>>
            ) {
                items = response.body()!!
                fragAdapter = FragAdapter(items)
                recyclerView.adapter = fragAdapter
                fragAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArrayList<pojoConvert>>, t: Throwable) {
                Log.i(s ,t.message!!)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment1, container, false)
    }

    class FragAdapter(private val list: List<pojoConvert> ):
           RecyclerView.Adapter<FragAdapter.FragViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return FragViewHolder(inflater,parent)
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: FragViewHolder, position: Int) {
            val pojoConvert:pojoConvert = list[position]
            holder.bind(pojoConvert)
        }


        class FragViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.retrofitviewholder, parent, false)) {
            private var id: TextView = itemView.findViewById(R.id.title)
            private var content: TextView = itemView.findViewById(R.id.content)

            fun bind(pojoConvert: pojoConvert) {
                id.text = pojoConvert.title
                content.text = pojoConvert.completed.toString()

            }

        }

    }
}
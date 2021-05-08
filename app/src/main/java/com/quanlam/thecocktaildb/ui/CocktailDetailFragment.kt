package com.quanlam.thecocktaildb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.quanlam.thecocktaildb.databinding.CocktailDetailFragmentBinding
import com.quanlam.thecocktaildb.dataservice.model.toOz
import com.squareup.picasso.Picasso

class CocktailDetailFragment(
    private val cocktailId: String,
) : Fragment() {

    companion object {
        fun newInstance(id: String) = CocktailDetailFragment(id)
    }

    private lateinit var viewModel: CocktailDetailViewModel
    private var binding: CocktailDetailFragmentBinding? = null
    private lateinit var ingredientListAdapter: IngredientListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CocktailDetailFragmentBinding.inflate(inflater, container, false)

        return binding?.root!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Init view model
        viewModel = ViewModelProvider(this).get(CocktailDetailViewModel::class.java)
        //Get some cocktails
        viewModel.getDrinkDetail(cocktailId, this::onDrinkFetched)
        binding?.backButton?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    //Callback for when the drink data is fetched
    private fun onDrinkFetched() {
        binding?.toolbarTitle?.text = viewModel.drink?.strDrink ?: ""
        Picasso.get().load(viewModel.drink?.strDrinkThumb).into(binding?.drinkImage)
        populateIngredientsList()
        binding?.instructionText?.text = viewModel.drink?.strInstructions
        createPieChart()
    }

    private fun populateIngredientsList() {
        if (viewModel.drink != null) {
            binding?.ingredientsLabel?.visibility = View.VISIBLE
            ingredientListAdapter =
                IngredientListAdapter(viewModel.drink!!.getIngredientsList(), context!!, cocktailId.toInt())

            with(binding?.ingredientsRecyclerView) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.adapter = ingredientListAdapter
            }
        }
    }

    private fun createPieChart() {
        if (viewModel.drink != null) {
            val pieChart = binding?.chart
            pieChart?.setUsePercentValues(false)
            val list = viewModel.drink!!.getIngredientsList()
            val pieEntryList = ArrayList<PieEntry>()
            for (i in list.indices) {
                pieEntryList.add(PieEntry(list[i].amount.toOz(), ""))
            }
            val pieDataSet = PieDataSet(pieEntryList, "ingredient")
            pieDataSet.setColors(IngredientColorUtil.getColorSet(list.size, cocktailId.toInt()), context)
            pieChart?.data = PieData(pieDataSet)
            pieChart?.isDrawHoleEnabled = false
            pieChart?.setDrawCenterText(false)
            pieChart?.description?.text = ""
            pieChart?.legend?.isEnabled = false
            pieChart?.setDrawEntryLabels(false)
            pieChart?.invalidate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}
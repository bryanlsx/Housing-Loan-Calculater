package pog.loo.bryan.loan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import pog.loo.bryan.loan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //initialise later
    private lateinit var binding: ActivityMainBinding
    private lateinit var builder: AlertDialog.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonCalculate.setOnClickListener {
            //initialising variable
            val sellingPrice = binding.editTextSellingPrice.text.toString().toInt()
            val downPayment = binding.editTextDownPayment.text.toString().toInt()
            //check if first time buyer
            val firstTimeBuyer = binding.checkBoxFirstTime.isChecked
            var actualAmount = 0.00
            var amount = 0.00

            var legalFee = 0.0f
            var stampDuty = 0.0f

            //check first time buyer down payment is 10% of selling price
            if(firstTimeBuyer){
                if(downPayment >= sellingPrice * 0.1){
                    //Acceptable
                    //Calc Legal Fees and Stamp duty
                    actualAmount = sellingPrice.toDouble() - downPayment.toDouble()

                    if(sellingPrice > 500000){
                        legalFee = ((500000 * 0.001) + (500000 * 0.008) + ((sellingPrice - 1000000)*0.005)).toFloat()
                        stampDuty = (sellingPrice * 0.005).toFloat()
                    }else{
                        legalFee = (500000 * 0.001).toFloat()
                        stampDuty = (actualAmount * 0.005).toFloat()
                    }
                }else{
                    //Unacceptable
                    //Show Dialog
                    builder = AlertDialog.Builder(this)
                    builder.setTitle("Warning! Insufficient Amount!")
                        .setMessage("Please Enter at lease 10% of your loan amount for first time buyer!")
                        .setNegativeButton("Okay"){dialogInterface,it ->
                            dialogInterface.cancel()
                        }
                        .show()
                }
            }else{
                //None First Time Buyer
                if(sellingPrice > 500000){
                    legalFee = ((500000 * 0.001) + (500000 * 0.008) + ((sellingPrice - 1000000)*0.005)).toFloat()
                    stampDuty = (sellingPrice * 0.005).toFloat()
                }else{
                    legalFee = (500000 * 0.001).toFloat()
                    stampDuty = (actualAmount * 0.005).toFloat()
                }
            }
            //Display Result
            val myLoan = Loan(legalFee.toString(), stampDuty.toString())
            binding.myLoan = myLoan
        }

        binding.buttonReset.setOnClickListener {
            val myLoan = Loan("", "")
            //binding.myLoan is defined in the data binding layout file
            //myLoan is a local variable
            binding.myLoan = myLoan
        }
    }
}
//
//  ViewController.swift
//  Swift_datum
//
//  Created by Tomislav Jakopec on 11.04.2017..
//  Copyright © 2017. Tomislav Jakopec. All rights reserved.
//

import Cocoa

class ViewController: NSViewController {
    
    

    @IBOutlet weak var datePicker: NSDatePicker!
    
    @IBAction func dohvati(_ sender: Any) {
        print("dohvaćam")
        let adresa = URL(string: "http://it.ffos.hr/fz2017/dohvati.php")!
        let request = URLRequest(url: adresa)
      
        let task = URLSession.shared.dataTask(with: request, completionHandler: { (data, response, error) -> Void in
            guard let data = data, let json = (try? JSONSerialization.jsonObject(with: data, options: [])) as? [String: Any], let datum = json["datumRCF3339"] as? String else {
                return
            }
            DispatchQueue.main.async(execute: {
                
                print(datum)
          
                let hr_HR = NSLocale(localeIdentifier: "hr_HR")
                let rfc3339DateFormatter = DateFormatter()
                rfc3339DateFormatter.locale = hr_HR as Locale!
                rfc3339DateFormatter.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ssXXX"
                rfc3339DateFormatter.timeZone = NSTimeZone(forSecondsFromGMT: 0) as TimeZone!
                
                let date = rfc3339DateFormatter.date(from: datum)
                
                self.datePicker.dateValue=date!
                
                //print( date)
            })
        }) 
        task.resume()
        
        
    }
    
  
    
    @IBAction func posalji(_ sender: Any) {
        print("datum ",datePicker.dateValue)
        
        print("Saljem")
        let adresa = URL(string: "http://it.ffos.hr/fz2017/posalji.php?datum=")!
        let request = URLRequest(url: adresa)
        
        let hr_HR = NSLocale(localeIdentifier: "hr_HR")
        let rfc3339DateFormatter = DateFormatter()
        rfc3339DateFormatter.locale = hr_HR as Locale!
        rfc3339DateFormatter.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ssXXX"
        rfc3339DateFormatter.timeZone = NSTimeZone(forSecondsFromGMT: 0) as TimeZone!
        
        let date = rfc3339DateFormatter.date(from: datum)
        
        let task = URLSession.shared.dataTask(with: request, completionHandler: { (data, response, error) -> Void in
            guard let data = data, let json = (try? JSONSerialization.jsonObject(with: data, options: [])) as? [String: Any], let datum = json["datumISO8601"] as? String else {
                return
            }
            DispatchQueue.main.async(execute: {
                
                print(datum)
                
                
                let rfc3339DateFormatter = DateFormatter()
                rfc3339DateFormatter.locale = hr_HR as Locale!
                rfc3339DateFormatter.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ssXXX"
                rfc3339DateFormatter.timeZone = NSTimeZone(forSecondsFromGMT: 0) as TimeZone!
                
                let date = rfc3339DateFormatter.date(from: datum)
                
                self.datePicker.dateValue=date!
                
                //print( date)
            })
        })
        task.resume()
        
        
    }
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override var representedObject: Any? {
        didSet {
        // Update the view, if already loaded.
        }
    }
    
    
   
    
    

}


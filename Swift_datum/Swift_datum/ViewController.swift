//
//  ViewController.swift
//  Swift_datum
//
//  Created by Tomislav Jakopec on 11.04.2017..
//  Copyright © 2017. Tomislav Jakopec. All rights reserved.
//  Kod nije najljepši, sklepan je da bude funkcionalan i odradi posao
//

import Cocoa

class ViewController: NSViewController {
    
    

    @IBOutlet weak var datePicker: NSDatePicker!
    
    var datumISO8601 = DateFormatter()
    
    var nazivVremenskeZone: String { return TimeZone.current.identifier }
    
    var server: String {return "http://it.ffos.hr/fz2017/"}
    
    var formatDatumaJSON: String {return "datumISO8601" }
    
    
    
    
    
    
    //https://developer.apple.com/swift/blog/?id=37
    @IBAction func dohvati(_ sender: Any) {
        let url = server + "dohvatiJSON.php?timezone=" + nazivVremenskeZone;
        let adresa = URL(string: url)!
        let request = URLRequest(url: adresa)
        let task = URLSession.shared.dataTask(with: request, completionHandler: { (data, response, error) -> Void in
            guard let data = data, let json = (try? JSONSerialization.jsonObject(with: data, options: [])) as? [String: Any], let datum = json[self.formatDatumaJSON] as? String else {
                return
            }
            DispatchQueue.main.async(execute: {
                let date = self.datumISO8601.date(from: datum)
                self.datePicker.dateValue=date!
            })
        })
        task.resume()
    }
    
  
    
    
    
    //https://code.bradymower.com/swift-3-apis-network-requests-json-getting-the-data-4aaae8a5efc0
    @IBAction func posalji(_ sender: Any) {
        let json: [String: Any] = ["timezone": nazivVremenskeZone,
                                   "datum": self.datumISO8601.string(from: self.datePicker.dateValue),
                                   "klijent": "swift"]
        let jsonData = try? JSONSerialization.data(withJSONObject: json)
        let url = URL(string: server + "insertJSON.php")!
        let request = NSMutableURLRequest(url: url)
        request.httpMethod = "POST"
        request.cachePolicy = NSURLRequest.CachePolicy.reloadIgnoringCacheData
        request.httpBody = jsonData
        let task = URLSession.shared.dataTask(with: request as URLRequest) { (data, response, error) in
            guard let _: Data = data, let _: URLResponse = response, error == nil else {
                print("Greška")
                return
            }
            let dataString = NSString(data: data!, encoding: String.Encoding.utf8.rawValue)
            print("Vratio server: \(String(describing: dataString))")
        }
        task.resume()
    }
    
    
    
    //poziva se prilikom otvaranja forme
    override func viewDidLoad() {
        let hr_HR = NSLocale(localeIdentifier: "hr_HR")
        self.datumISO8601 = DateFormatter()
        self.datumISO8601.locale = hr_HR as Locale!
        self.datumISO8601.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ssXXX"
        self.datumISO8601.timeZone = TimeZone.current

    }
    
    

}

